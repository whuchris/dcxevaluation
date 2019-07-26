package com.whu.service.impl;

import com.whu.mapper.*;
import com.whu.pojo.*;
import com.whu.service.Type4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class Type4ServiceImpl implements Type4Service
{
    @Autowired
    EnvirBenefit4Mapper envirBenefit4Mapper;

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
     * @param envirBenefit4
     * @param socialBenefit
     * @param econoBenefit
     * @return 1：成功； 0：未知错误； -1：项目已经评审
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public int insertType4Score(EnvirBenefit4 envirBenefit4, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
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
            envirBenefit4Mapper.insertScore(envirBenefit4);
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
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }


    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    @Override
    public Map<String, Benefit> queryScoreByProjectIdAndState(Long projectId, int state,Long expertId)
    {
        Map<String, Benefit> result = new HashMap<>();
        try
        {
            EconoBenefit econoBenefit = econoBenefitMapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            SocialBenefit socialBenefit = socialBenefitMapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            EnvirBenefit4 envirBenefit4 = envirBenefit4Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
            result.put("econoBenefit", econoBenefit);
            result.put("socialBenefit", socialBenefit);
            result.put("envirBenefit4",envirBenefit4);
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
            List<EnvirBenefit4> envirBenefit4s = envirBenefit4Mapper.queryScoresByProjectIdAndState(projectId, state);
            int size = econoBenefits.size();
            float totalOperationPerformance = 0.0f;
            float totalEffect = 0.0f;
            float totalCulturalEnvir = 0.0f;
            float totalPhysicalEnvir = 0.0f;
            float totalDecorationMaterial = 0.0f;
            float totalDecorationTechnology = 0.0f;
            for(int i = 0; i < size; ++i)
            {
                totalOperationPerformance += econoBenefits.get(i).getOperationPerformance();
                totalEffect += socialBenefits.get(i).getEffect();
                totalCulturalEnvir += envirBenefit4s.get(i).getCulturalEnvir();
                totalPhysicalEnvir += envirBenefit4s.get(i).getPhysicalEnvir();
                totalDecorationMaterial += envirBenefit4s.get(i).getDecorationMaterial();
                totalDecorationTechnology += envirBenefit4s.get(i).getDecorationTechnology();
            }
            result.put("avgOperationPerformance", totalOperationPerformance / size);
            result.put("avgEffect", totalEffect / size);
            result.put("avgCulturalEnvir", totalCulturalEnvir / size);
            result.put("avgPhysicalEnvir", totalPhysicalEnvir / size);
            result.put("avgDecorationMaterial", totalDecorationMaterial / size);
            result.put("avgDecorationTechnology", totalDecorationTechnology / size);
        }
        catch (Exception e)
        {
            return null;
        }
        return result;
    }
}
