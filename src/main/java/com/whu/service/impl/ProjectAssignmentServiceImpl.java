package com.whu.service.impl;

import com.whu.mapper.AdminMapper;
import com.whu.mapper.ProjectAssignmentMapper;
import com.whu.mapper.ProjectMapper;
import com.whu.pojo.*;
import com.whu.service.ProjectAssignmentService;
import com.whu.service.Type1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService
{
    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    Type1Service type1Service;

    @Autowired
    AdminMapper adminMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int insertAssignment(int state, List<Long> projectIdList, Long expertId)
    {
        for(Long projectId : projectIdList)
        {
            Long result = projectAssignmentMapper.queryExpertIdByProjectIdAndState(projectId,state,expertId);
            //查看项目是否已经分配给该专家
            if(result != null)
            {
                return -2;//存在则不需要
            }
        }
        try
        {
            projectAssignmentMapper.insertAssignments(state, projectIdList, expertId);
            for(Long projectId : projectIdList)
            {
                Project project = projectMapper.queryProjectById(projectId);
                if(state == 1)
                    project.setfGrade(-1.0f);
                else
                    project.setlGrade(-1.0f);
                projectMapper.updateProject(project);
            }
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        return 1;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int insertAssignments(int state, List<Long> projectIdList, Set<Long> expertIdSet)
    {

        List<Long> expertIdList = new ArrayList<>(expertIdSet);


        Collections.shuffle(projectIdList); //打乱项目的顺序，使其能够随机排序
        Collections.shuffle(expertIdList);

        int pNum = projectIdList.size(); //项目数量
        int eNum = expertIdList.size();  //专家数量
        if(eNum < 7)  //专家数小于7，所有专家都需要评审一次该类型的所有项目
        {
            for(Long expertId : expertIdList)
            {
                for(Long  projectId : projectIdList)
                {
                    Long result = projectAssignmentMapper.queryExpertIdByProjectIdAndState(projectId,state,expertId);
                    if(result != null ) //查看项目是否已经分配给该专家
                    {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return -2;//存在则不需要
                    }
                }
                try
                {
                    projectAssignmentMapper.insertAssignments(state, projectIdList, expertId);
            }
                catch (Exception e)
                {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return -1;
                }
            }
            return 1;
        }
        else
        {
            int total = pNum * 7;
            int quotient = total / eNum;
            int remainder = total % eNum;
            Map<Long, List<Long>> assignment = new HashMap<>(); //存放每个专家对应的项目Id列表
            int index = 0;
            for(Long expertId : expertIdList)
            {
                List<Long> idList = new ArrayList<>(); //用来存放分配给当前专家的项目id集合
                for(int i = 0; i < quotient; i++) //每个专家分配quotient次
                {
                    idList.add(projectIdList.get(index));
                    index = (index + 1) % pNum;      //索引+1
                }
                for(Long  projectId : idList)
                {
                    Long result = projectAssignmentMapper.queryExpertIdByProjectIdAndState(projectId,state,expertId);
                    if(result != null ) //查看项目是否已经分配给该专家
                    {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return -2;//存在则不需要
                    }
                }
                try
                {
                    projectAssignmentMapper.insertAssignments(state, idList, expertId);
                }
                catch (Exception e)
                {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return -1;
                }
            }

            for(Long expertId: expertIdList)
            {
                if(remainder < 0)
                    break;
                Long projectId = projectIdList.get(index);
                Long result = projectAssignmentMapper.queryExpertIdByProjectIdAndState(projectId,state,expertId);
                if(result == null )
                {
                    List<Long> idList = new ArrayList<>();
                    idList.add(projectId);
                    try
                    {
                        projectAssignmentMapper.insertAssignments(state, idList, expertId);
                        remainder--;
                        index = (index + 1) % pNum;      //索引+1
                    }
                    catch (Exception e)
                    {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return -1;
                    }
                }
            }
            if(state == 1)
                adminMapper.changeStateAtFirstAssessment();
            else
                adminMapper.changeStateAtLastAssessment();
            return 1;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int deleteAssignments(List<Long> projectIdList, int state)
    {
        if(state != 1 && state != 2)
            return -3;

        for(Long projectId: projectIdList)
        {
            //判断是否有项目已评分
            //Map<String, Benefit> score = type1Service.queryScoreByProjectIdAndState(projectId,state);
            //EconoBenefit ecb = (EconoBenefit)score.get("econoBenefit");
            //if(ecb != null)
                //return -2;  //已评分返回-2
            List<ProjectAssignment> projectAssignments = projectAssignmentMapper.
                    queryAssignmentsByProjectIdAndState(projectId,state);
            for(ProjectAssignment assignment: projectAssignments)
                if(assignment.getFinish() == 1)
                    return -2; //已经评分无法撤回

        }
        try
        {
            projectAssignmentMapper.deleteAssignments(projectIdList, state);
            for(Long projectId : projectIdList)
            {
                Project project = projectMapper.queryProjectById(projectId);
                if(state == 1)
                    project.setAssessmentState(0);
                else if(state == 2)
                    project.setAssessmentState(20);
                projectMapper.updateProject(project);
            }
            return 1;
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int deleteAssignment(Long projectId, Long expertId, int state)
    {
        if(state != 1 && state != 2)
            return -3;

        ProjectAssignment projectAssignment = projectAssignmentMapper.
                queryAssignmentByProjectIdAndExpertIdAndState(projectId,expertId,state);
        if(projectAssignment != null && projectAssignment.getFinish() == 1)
            return -2; //已经评分无法撤回

        projectAssignmentMapper.deleteAssignment(projectId,expertId,state);

        //删除一个专家之后，判断该专家是不是刚好是最后一个未评审该项目的专家
        Project project = projectMapper.queryProjectById(projectId);
        List<ProjectAssignment> projectAssignments = //查看该项目的分配到的专家是否已经全部评审完毕
                projectAssignmentMapper.queryAssignmentsByProjectIdAndState(projectId,state);
        List<Float> gradeList = new ArrayList<>();
        for(ProjectAssignment assignment: projectAssignments)
        {
            if(assignment.getFinish() == 1)  //当前专家是否评分
                gradeList.add(assignment.getGrade());
            else
                break;                //如果存在一个没有评分的专家就直接跳出循环
        }
        int gradeNum = gradeList.size();
        if(projectAssignments.size() == gradeNum)  //数目相等说明该项目已经评审完毕
        {
            Collections.sort(gradeList);   //成绩升序排列
            float total = 0.0f;
            for(int i = 1; i < gradeNum - 1; i++)
            {
                total += gradeList.get(i);
            }
            if(state == 1)
                if(gradeNum > 2)
                    project.setfGrade((total-gradeList.get(0)-gradeList.get(gradeNum-1)) / (gradeNum - 2 ));
                else
                    project.setfGrade(total / (gradeNum));
            else
                if(gradeNum > 2)
                    project.setlGrade((total-gradeList.get(0)-gradeList.get(gradeNum-1)) / (gradeNum - 2 ));
                else
                    project.setlGrade(total / (gradeNum));
            projectMapper.updateProject(project);
        }
        return 1;
    }
}
