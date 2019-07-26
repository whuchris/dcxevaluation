package com.whu.service.impl;

import com.whu.mapper.ProjectAssignmentMapper;
import com.whu.mapper.ProjectMapper;
import com.whu.pojo.Project;
import com.whu.service.ProjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService
{
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

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
    public int selectProjectsAtFirstAssessment(List<Long> projectIdListToNext,
                                               List<Long> projectIdListToRemain)
    {
        //进入会评的项目
        for(Long id : projectIdListToNext)
        {
            Project project = projectMapper.queryProjectById(id);
            if(project.getAssessmentState() == 1 && project.getfGrade() >= 0.0f)
            {
                if(project.getAssessmentState() == 2)
                    continue;
                else
                {
                    project.setAssessmentState(2);
                    projectMapper.updateProject(project);
                }
            }
            else
            {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return -4;
            }
        }
        /*
        //遗留的项目，默认被淘汰
        for(Long id: projectIdListToRemain)
        {
            Project project = projectMapper.queryProjectById(id);
            if(project.getAssessmentState() == 1)
            {
                continue;
            }
            else
            {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return -4;
            }
        }*/
        return 1;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public int selectProjectsAtLastAssessment(List<Long> projectIdListToPromotion,
                                              List<Long> projectIdListToRemain)
    {
        for(Long id :projectIdListToPromotion)
        {
            Project project = projectMapper.queryProjectById(id);
            if(project.getAssessmentState() == 2  && project.getlGrade() >= 0.0f)
            {
                project.setAssessmentState(3);
                projectMapper.updateProject(project);
            }
            else
            {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return -4;
            }
        }
        /*
        //遗留的项目，默认被淘汰
        for(Long id: projectIdListToRemain)
        {
            Project project = projectMapper.queryProjectById(id);
            if(project.getAssessmentState() != 21)
            {
                project.setAssessmentState(22);
                projectMapper.updateProject(project);
            }
            else
            {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return -4;
            }
        }*/
        return 1;
    }
}
