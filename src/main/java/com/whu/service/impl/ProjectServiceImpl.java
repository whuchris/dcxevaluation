package com.whu.service.impl;

import com.whu.mapper.*;
import com.whu.pojo.*;
import com.whu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService
{
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    @Autowired
    Type1Service type1Service;

    @Autowired
    Type2Service type2Service;

    @Autowired
    Type3Service type3Service;

    @Autowired
    Type4Service type4Service;

    @Autowired
    EnvirBenefit1Mapper envirBenefit1Mapper;

    @Autowired
    EnvirBenefit2Mapper envirBenefit2Mapper;

    @Autowired
    EnvirBenefit3Mapper envirBenefit3Mapper;

    @Autowired
    EnvirBenefit4Mapper envirBenefit4Mapper;

    @Autowired
    SocialBenefitMapper socialBenefitMapper;

    @Autowired
    EconoBenefitMapper econoBenefitMapper;


    @Override
    public Project queryProjectById(Long id)
    {
        return projectMapper.queryProjectById(id);
    }


    @Override
    public int updateProject(Project project)
    {
        return projectMapper.updateProject(project);
    }


    @Override
    public List<Project> queryProjectsByExpertIdAndState(Long expertId, int state)
    {
        List<Long> projectIdList = projectAssignmentMapper.queryProjectIdsByExpertIdAndState(expertId, state);
        if(projectIdList.size() == 0)
            return null;
        else
            return projectMapper.queryProjectsByProjectIds(projectIdList, expertId, state);
    }

    @Override
    public List<Project> queryAllProjects()
    {
        return projectMapper.queryAllProjects();
    }

    @Override
    public List<Project> queryProjectsByState(int state)
    {
        return projectMapper.queryProjectsByState(state);
    }

    @Override
    public List<Project> queryUnassignedProjectsF()
    {
        return projectMapper.queryUnassignedProjectsF();
    }

    @Override
    public List<Project> queryUnassignedProjectsL()
    {
        return projectMapper.queryUnassignedProjectsL();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Map<String,Object> selectProjectsAtFirstAssessment(List<Long> projectIdListToNext,
                                               List<Long> projectIdListToRemain)
    {
        Map<String, Object> result = new HashMap<>();
        //遗留到初评的项目，默认被淘汰
        for(Long id: projectIdListToRemain)
        {
            Project project = projectMapper.queryProjectById(id);
            int state = project.getAssessmentState();
            if(state == 1)
            {
                continue;
            }
            else if(state == 2)
            {
                List<ProjectAssignment> projectAssignments =
                        projectAssignmentMapper.queryAssignmentsByProjectIdAndState(id,state);
                if(projectAssignments == null)  //还未分配
                {
                    project.setAssessmentState(1);
                    projectMapper.updateProject(project);
                }
                else     //项目已经分配
                {
                    for(ProjectAssignment assignment:projectAssignments)
                    {
                        if(assignment.getFinish() == 1)   //存在已经评分的专家
                        {
                            result.put("code",-4);
                            result.put("errProjectName",project.getName());
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return result;
                        }
                    }
                    for(ProjectAssignment assignment:projectAssignments)   //都没有评分，直接撤回
                        projectAssignmentMapper.
                                deleteAssignment(assignment.getProjectId(), assignment.getExpertId(),state);
                }

            }
        }
        //进入会评的项目
        for(Long id : projectIdListToNext)
        {
            Project project = projectMapper.queryProjectById(id);
            int state = project.getAssessmentState();
            if(state == 1 /*&& project.getfGrade() >= -1*/)
            {
                project.setAssessmentState(2);
                projectMapper.updateProject(project);
            }
            else if(state == 2)
            {
                continue;
            }
        }
        result.put("code",1);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public int selectProjectsAtLastAssessment(List<Long> projectIdListToNext,
                                              List<Long> projectIdListToRemain)
    {
        for(Long projectId :projectIdListToNext)
        {
            Project project = projectMapper.queryProjectById(projectId);
            float finalGrade = project.getlGrade();
            int state = project.getAssessmentState();
            if(state == 2)
            {
                project.setAssessmentState(3);
                project.setFinalGrade(finalGrade);
                int type = project.getPrize().getsType();
                ProjectAssignment projectAssignment = new ProjectAssignment();
                EconoBenefit econoBenefit = new EconoBenefit();
                SocialBenefit socialBenefit = new SocialBenefit();
                if(type == 1)
                {
                    EnvirBenefit1 envirBenefit1 = new EnvirBenefit1();
                    Map<String, Float> avgScore1 =
                            type1Service.queryScoresByProjectIdAndState(projectId, state);
                    econoBenefit.setOperationPerformance(avgScore1.get("avgOperationPerformance"));
                    econoBenefit.setExpertId(-1L);
                    econoBenefit.setProjectId(projectId);
                    econoBenefit.setState(3);

                    socialBenefit.setEffect(avgScore1.get("avgEffect"));
                    socialBenefit.setExpertId(-1L);
                    socialBenefit.setProjectId(projectId);
                    socialBenefit.setState(3);

                    envirBenefit1.setArt(avgScore1.get("avgArt"));
                    envirBenefit1.setIndoorEnvir(avgScore1.get("avgIndoorEnvir"));
                    envirBenefit1.setConstructionManagement(avgScore1.get("avgConstructionManagement"));
                    envirBenefit1.setInnovationEvaluation(avgScore1.get("avgInnovationEvaluation"));
                    envirBenefit1.setOperationManagement(avgScore1.get("avgOperationManagement"));
                    envirBenefit1.setOutdoorEnvir(avgScore1.get("avgOutdoorEnvir"));
                    envirBenefit1.setResourceUtilization(avgScore1.get("avgResourceUtilization"));
                    envirBenefit1.setExpertId(-1L);
                    envirBenefit1.setProjectId(projectId);
                    envirBenefit1.setState(3);

                    projectAssignment.setExpertId(-1L);
                    projectAssignment.setProjectId(projectId);
                    projectAssignment.setState(3);
                    projectAssignment.setGrade(finalGrade);
                    projectAssignment.setFinish(1);

                    envirBenefit1Mapper.insertScore(envirBenefit1);
                    socialBenefitMapper.insertScore(socialBenefit);
                    econoBenefitMapper.insertScore(econoBenefit);
                    projectAssignmentMapper.insertProjectAssignment(projectAssignment);

                }
                else if(type == 2)
                {
                    EnvirBenefit2 envirBenefit2 = new EnvirBenefit2();
                    Map<String, Float> avgScore2 =
                            type2Service.queryScoresByProjectIdAndState(projectId, state);
                    econoBenefit.setOperationPerformance(avgScore2.get("avgOperationPerformance"));
                    econoBenefit.setExpertId(-1L);
                    econoBenefit.setProjectId(projectId);
                    econoBenefit.setState(3);

                    socialBenefit.setEffect(avgScore2.get("avgEffect"));
                    socialBenefit.setExpertId(-1L);
                    socialBenefit.setProjectId(projectId);
                    socialBenefit.setState(3);

                    envirBenefit2.setArt(avgScore2.get("avgArt"));
                    envirBenefit2.setEnvir(avgScore2.get("avgEnvir"));
                    envirBenefit2.setGreenTransportation(avgScore2.get("avgGreenTransportation"));
                    envirBenefit2.setInformationManagement(avgScore2.get("avgInformationManagement"));
                    envirBenefit2.setLandUsing(avgScore2.get("avgLandUsing"));
                    envirBenefit2.setExpertId(-1L);
                    envirBenefit2.setProjectId(projectId);
                    envirBenefit2.setState(3);

                    projectAssignment.setExpertId(-1L);
                    projectAssignment.setProjectId(projectId);
                    projectAssignment.setState(3);
                    projectAssignment.setGrade(finalGrade);
                    projectAssignment.setFinish(1);

                    envirBenefit2Mapper.insertScore(envirBenefit2);
                    socialBenefitMapper.insertScore(socialBenefit);
                    econoBenefitMapper.insertScore(econoBenefit);
                    projectAssignmentMapper.insertProjectAssignment(projectAssignment);
                }
                else if(type == 3)
                {
                    EnvirBenefit3 envirBenefit3 = new EnvirBenefit3();
                    Map<String, Float> avgScore2 =
                            type3Service.queryScoresByProjectIdAndState(projectId, state);
                    econoBenefit.setOperationPerformance(avgScore2.get("avgOperationPerformance"));
                    econoBenefit.setExpertId(-1L);
                    econoBenefit.setProjectId(projectId);
                    econoBenefit.setState(3);

                    socialBenefit.setEffect(avgScore2.get("avgEffect"));
                    socialBenefit.setExpertId(-1L);
                    socialBenefit.setProjectId(projectId);
                    socialBenefit.setState(3);

                    envirBenefit3.setArt(avgScore2.get("avgArt"));
                    envirBenefit3.setEnvirFriendliness(avgScore2.get("avgEnvirFriendliness"));
                    envirBenefit3.setProjectFunction(avgScore2.get("avgProjectFunction"));
                    envirBenefit3.setProjectTechnology(avgScore2.get("avgProjectTechnology"));
                    envirBenefit3.setExpertId(-1L);
                    envirBenefit3.setProjectId(projectId);
                    envirBenefit3.setState(3);

                    projectAssignment.setExpertId(-1L);
                    projectAssignment.setProjectId(projectId);
                    projectAssignment.setState(3);
                    projectAssignment.setGrade(finalGrade);
                    projectAssignment.setFinish(1);

                    envirBenefit3Mapper.insertScore(envirBenefit3);
                    socialBenefitMapper.insertScore(socialBenefit);
                    econoBenefitMapper.insertScore(econoBenefit);
                    projectAssignmentMapper.insertProjectAssignment(projectAssignment);
                }
                else
                {
                    EnvirBenefit4 envirBenefit4 = new EnvirBenefit4();
                    Map<String, Float> avgScore2 =
                            type4Service.queryScoresByProjectIdAndState(projectId, state);
                    econoBenefit.setOperationPerformance(avgScore2.get("avgOperationPerformance"));
                    econoBenefit.setExpertId(-1L);
                    econoBenefit.setProjectId(projectId);
                    econoBenefit.setState(3);

                    socialBenefit.setEffect(avgScore2.get("avgEffect"));
                    socialBenefit.setExpertId(-1L);
                    socialBenefit.setProjectId(projectId);
                    socialBenefit.setState(3);

                    envirBenefit4.setCulturalEnvir(avgScore2.get("avgCulturalEnvir"));
                    envirBenefit4.setDecorationMaterial(avgScore2.get("avgDecorationMaterial"));
                    envirBenefit4.setDecorationTechnology(avgScore2.get("avgDecorationTechnology"));
                    envirBenefit4.setPhysicalEnvir(avgScore2.get("avgPhysicalEnvir"));
                    envirBenefit4.setExpertId(-1L);
                    envirBenefit4.setProjectId(projectId);
                    envirBenefit4.setState(3);

                    projectAssignment.setExpertId(-1L);
                    projectAssignment.setProjectId(projectId);
                    projectAssignment.setState(3);
                    projectAssignment.setGrade(finalGrade);
                    projectAssignment.setFinish(1);

                    envirBenefit4Mapper.insertScore(envirBenefit4);
                    socialBenefitMapper.insertScore(socialBenefit);
                    econoBenefitMapper.insertScore(econoBenefit);
                    projectAssignmentMapper.insertProjectAssignment(projectAssignment);
                }

                projectMapper.updateProject(project);
            }
            else if(state == 3)
            {
                continue;
            }
        }
        //遗留的项目，默认被淘汰
        for(Long id: projectIdListToRemain)
        {
            Project project = projectMapper.queryProjectById(id);
            int state = project.getAssessmentState();
            int type = project.getPrize().getsType();
            if(state == 3)
            {
                EconoBenefit econoBenefit = econoBenefitMapper.
                        queryScoreByProjectIdAndState(id,state,-1L);
                SocialBenefit socialBenefit = socialBenefitMapper.
                        queryScoreByProjectIdAndState(id,state,-1L);
                if(type == 1)
                {
                    EnvirBenefit1 envirBenefit1 = envirBenefit1Mapper.
                            queryScoreByProjectIdAndState(id,state,-1L);
                    envirBenefit1Mapper.deleteScore(envirBenefit1);

                }
                else if(type == 2)
                {
                    EnvirBenefit2 envirBenefit2 = envirBenefit2Mapper.
                            queryScoreByProjectIdAndState(id,state,-1L);
                    envirBenefit2Mapper.deleteScore(envirBenefit2);
                }
                else if(type == 3)
                {
                    EnvirBenefit3 envirBenefit3 = envirBenefit3Mapper.
                            queryScoreByProjectIdAndState(id,state,-1L);
                    envirBenefit3Mapper.deleteScore(envirBenefit3);
                }
                else
                {
                    EnvirBenefit4 envirBenefit4 = envirBenefit4Mapper.
                            queryScoreByProjectIdAndState(id,state,-1L);
                    envirBenefit4Mapper.deleteScore(envirBenefit4);
                }
                econoBenefitMapper.deleteScore(econoBenefit);
                socialBenefitMapper.deleteScore(socialBenefit);
                projectAssignmentMapper.deleteAssignment(id,-1L,state);
                project.setAssessmentState(2);
                project.setFinalGrade(-1.0f);
                project.setPrizeClass(5);
                projectMapper.updateProject(project);
            }
            else if(state == 2)
            {
                continue;
            }
        }
        return 1;
    }
}
