package com.whu.service.impl;

import com.whu.mapper.*;
import com.whu.service.*;
import com.whu.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class Type1ServiceImpl implements Type1Service
{
    @Autowired
    EnvirBenefit1Mapper envirBenefit1Mapper;

    @Autowired
    SocialBenefitMapper socialBenefitMapper;

    @Autowired
    EconoBenefitMapper econoBenefitMapper;

    @Autowired
    ProjectService projectMapper;

    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    /**
     * 返回提交成绩的结果
     * @param envirBenefit1
     * @param socialBenefit
     * @param econoBenefit
     * @return 1：成功； 0：未知错误； -1：项目已经评审; -2: 无权评审
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int insertType1Score(EnvirBenefit1 envirBenefit1, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                                float grade)
    {
        /**
         * 从前端获取到projectId和state
         * 如果表中已经存在记录说明该项目已经被评分过
         */
        Long projectId = econoBenefit.getProjectId();
        Long expertId = econoBenefit.getExpertId();
        int state = econoBenefit.getState();

        //判断是否有权限
        ProjectAssignment projectAssignment =
                projectAssignmentMapper.queryAssignmentByProjectIdAndExpertIdAndState(projectId, expertId, state);
        if(projectAssignment == null) //说明管理员撤销分配给该专家该项目
            return -2;

        //判断是否已评分
        /*
        Map<String, Benefit> score = queryScoreByProjectIdAndState(projectId,state);
        EnvirBenefit1 eb = (EnvirBenefit1)score.get("envirBenefit1");
        SocialBenefit sb = (SocialBenefit)score.get("socialBenefit");
        EconoBenefit  ecb = (EconoBenefit)score.get("econoBenefit");

        if((eb != null) || (sb != null) || (ecb != null) )
            return -1;  //已评分返回-1*/
        if(projectAssignment.getFinish() == 1)
            return -1;

        Project project = projectMapper.queryProjectById(projectId);

        try
        {
            envirBenefit1Mapper.insertScore(envirBenefit1);
            socialBenefitMapper.insertScore(socialBenefit);
            econoBenefitMapper.insertScore(econoBenefit);

            projectAssignment.setGrade(grade);
            projectAssignment.setFinish(1);
            projectAssignmentMapper.updateAssignment(projectAssignment);

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
                for(int i = 0; i < gradeNum; i++)
                {
                    total += gradeList.get(i);
                }
                if(state == 1)
                    /*if(gradeNum > 2)
                        project.setfGrade((total-gradeList.get(0)-gradeList.get(gradeNum-1)) / (gradeNum - 2 ));
                    else
                        project.setfGrade(total / (gradeNum));*/ //去掉最高分最低分的版本
                    project.setfGrade(total / (gradeNum)); //不去掉最高分最低分的版本
                else
                    /*if(gradeNum > 2)
                        project.setlGrade((total-gradeList.get(0)-gradeList.get(gradeNum-1)) / (gradeNum - 2 ));
                    else
                        project.setlGrade(total / (gradeNum));*/ //去掉最高分最低分的版本
                    project.setlGrade(total / (gradeNum)); //不去掉最高分最低分的版本
                projectMapper.updateProject(project);
            }
            return 1;
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }


    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public Map<String, Benefit> queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        Map<String, Benefit> result = new HashMap<>();
        try
        {
            EconoBenefit econoBenefit = econoBenefitMapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            SocialBenefit socialBenefit = socialBenefitMapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            EnvirBenefit1 envirBenefit1 = envirBenefit1Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            result.put("econoBenefit", econoBenefit);
            result.put("socialBenefit", socialBenefit);
            result.put("envirBenefit1",envirBenefit1);
        }
        catch (Exception e)
        {
            return null;
        }
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public Map<String, Float> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        Map<String, Float> result = new HashMap<>();
        try
        {
            List<ProjectAssignment> projectAssignments = projectAssignmentMapper.
                    queryAssignmentsByProjectIdAndStateOrderByGrade(projectId,state);

            //Long maxGradeExpertId = projectAssignments.get(projectAssignments.size() - 1).getExpertId();
            //Long minGradeExpertId = projectAssignments.get(0).getExpertId();去掉最高分最低分的版本

            List<EconoBenefit> econoBenefits = econoBenefitMapper.
                    queryScoresByProjectIdAndStateOrderByExpertId(projectId, state);
            List<SocialBenefit> socialBenefits = socialBenefitMapper.
                    queryScoresByProjectIdAndStateOrderByExpertId(projectId, state);
            List<EnvirBenefit1> envirBenefit1s = envirBenefit1Mapper.
                    queryScoresByProjectIdAndStateOrderByExpertId(projectId, state);
            int size = econoBenefits.size();
            float totalOperationPerformance = 0.0f;
            float totalArt = 0.0f;
            float totalIndoorEnvir = 0.0f;
            float totalConstructionManagement = 0.0f;
            float totalInnovationEvaluation = 0.0f;
            float totalOperationManagement = 0.0f;
            float totalOutdoorEnvir = 0.0f;
            float totalResourceUtilization = 0.0f;
            float totalEffect = 0.0f;
            for(int i = 0; i < size; ++i)
            {
                EnvirBenefit1 envirBenefit1 = envirBenefit1s.get(i);
                EconoBenefit econoBenefit = econoBenefits.get(i);
                /*
                if(size > 2 &&
                        ((econoBenefit.getExpertId().equals(maxGradeExpertId))
                                || (econoBenefit.getExpertId().equals(minGradeExpertId))))
                    continue;*/ //去掉最高分最低分

                SocialBenefit socialBenefit = socialBenefits.get(i);

                totalOperationPerformance += econoBenefit.getOperationPerformance();
                totalEffect += socialBenefit.getEffect();
                totalArt += envirBenefit1.getArt();
                totalConstructionManagement += envirBenefit1.getConstructionManagement();
                totalIndoorEnvir += envirBenefit1.getIndoorEnvir();
                totalInnovationEvaluation += envirBenefit1.getInnovationEvaluation();
                totalOperationManagement += envirBenefit1.getOperationManagement();
                totalOutdoorEnvir += envirBenefit1.getOutdoorEnvir();
                totalResourceUtilization += envirBenefit1.getResourceUtilization();
            }
            //size = size > 2? (size-2): size; 用于去掉最高分最低分的
            result.put("avgOperationPerformance", (float)(Math.round(totalOperationPerformance / size *100))/100);
            result.put("avgEffect", (float)(Math.round(totalEffect / size *100))/100);
            result.put("avgArt", (float)(Math.round(totalArt / size *100))/100);
            result.put("avgIndoorEnvir", (float)(Math.round(totalIndoorEnvir / size *100))/100);
            result.put("avgConstructionManagement", (float)(Math.round(totalConstructionManagement / size *100))/100);
            result.put("avgInnovationEvaluation", (float)(Math.round(totalInnovationEvaluation / size *100))/100);
            result.put("avgOperationManagement", (float)(Math.round(totalOperationManagement / size *100))/100);
            result.put("avgOutdoorEnvir", (float)(Math.round(totalOutdoorEnvir / size *100))/100);
            result.put("avgResourceUtilization", (float)(Math.round(totalResourceUtilization / size *100))/100);

        }
        catch (Exception e)
        {
            return null;
        }
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int alterType1Score(EnvirBenefit1 envirBenefit1, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                               float grade)
    {
        try
        {
            envirBenefit1Mapper.updateScore(envirBenefit1);
            socialBenefitMapper.updateScore(socialBenefit);
            econoBenefitMapper.updateScore(econoBenefit);
            Long projectId = econoBenefit.getProjectId();
            ProjectAssignment projectAssignment = projectAssignmentMapper.
                    queryAssignmentByProjectIdAndExpertIdAndState(projectId,-1L,3);
            projectAssignment.setGrade(grade);
            projectAssignmentMapper.updateAssignment(projectAssignment);
            Project project = projectMapper.queryProjectById(projectId);
            project.setFinalGrade(grade);
            projectMapper.updateProject(project);
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        return 1;
    }
}
