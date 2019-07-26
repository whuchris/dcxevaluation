package com.whu.service.impl;

import com.whu.mapper.*;
import com.whu.pojo.*;
import com.whu.service.Type2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class Type2ServiceImpl implements Type2Service
{
    @Autowired
    EnvirBenefit2Mapper envirBenefit2Mapper;

    @Autowired
    SocialBenefitMapper socialBenefitMapper;

    @Autowired
    EconoBenefitMapper econoBenefitMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    /**
     * 返回提交成绩的结果
     * @param envirBenefit2
     * @param socialBenefit
     * @param econoBenefit
     * @return 1：成功； 0：未知错误； -1：项目已经评审
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int insertType2Score(EnvirBenefit2 envirBenefit2, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
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
            envirBenefit2Mapper.insertScore(envirBenefit2);
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
            EnvirBenefit2 envirBenefit2 = envirBenefit2Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            result.put("econoBenefit", econoBenefit);
            result.put("socialBenefit", socialBenefit);
            result.put("envirBenefit2",envirBenefit2);
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
            List<EconoBenefit> econoBenefits = econoBenefitMapper.queryScoresByProjectIdAndState(projectId, state);
            List<SocialBenefit> socialBenefits = socialBenefitMapper.queryScoresByProjectIdAndState(projectId, state);
            List<EnvirBenefit2> envirBenefit2s = envirBenefit2Mapper.queryScoresByProjectIdAndState(projectId, state);
            int size = econoBenefits.size();
            float totalOperationPerformance = 0.0f;
            float totalEffect = 0.0f;
            float totalArt = 0.0f;
            float totalGreenTransportation = 0.0f;
            float totalInformationManagement = 0.0f;
            float totalEnvir = 0.0f;
            float totalLandUsing = 0.0f;
            for(int i = 0; i < size; ++i)
            {
                totalOperationPerformance += econoBenefits.get(i).getOperationPerformance();
                totalEffect += socialBenefits.get(i).getEffect();
                totalArt += envirBenefit2s.get(i).getArt();
                totalInformationManagement += envirBenefit2s.get(i).getInformationManagement();
                totalGreenTransportation += envirBenefit2s.get(i).getGreenTransportation();
                totalEnvir += envirBenefit2s.get(i).getEnvir();
                totalLandUsing += envirBenefit2s.get(i).getLandUsing();
            }
            result.put("avgOperationPerformance", totalOperationPerformance / size);
            result.put("avgEffect", totalEffect / size);
            result.put("avgArt", totalArt / size);
            result.put("avgInformationManagement", totalInformationManagement / size);
            result.put("avgGreenTransportation", totalGreenTransportation / size);
            result.put("avgEnvir", totalEnvir / size);
            result.put("avgLandUsing", totalLandUsing / size);
        }
        catch (Exception e)
        {
            return null;
        }
        return result;
    }
}
