package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Project;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProjectServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    ProjectService projectService;

    @Test
    public void queryProjectById()
    {
        Project project = projectService.queryProjectById(2L);
        log.info("ProjectServiceTest.queryProjectById: " + project.toString());
    }


    @Test
    public void updateProject()
    {
        Project project = projectService.queryProjectById(1L);
        project.setAssessmentState(1);
        int code = projectService.updateProject(project);
        log.info("ProjectServiceTest.updateProject: " + code);
    }

    @Test
    public void queryProjectsByProjectIds()
    {
        List<Long> list = new ArrayList<>();

        List<Project> projects = projectService.queryProjectsByExpertIdAndState(45L,1);
        log.info("ProjectServiceTest.queryProjectsByProjectIds: " + projects);
    }

    @Test
    public void queryAllProjects()
    {
        List<Project> projects = projectService.queryAllProjects();
        log.info("ProjectServiceTest.queryAllProjects: " + projects);
    }

    @Test
    public void queryUnassignedProjectsF()
    {
        List<Project> projects = projectService.queryUnassignedProjectsF();
        log.info("ProjectServiceTest.queryUnassignedProjectsF: " + projects);
    }

    @Test
    public void queryUnassignedProjectsL()
    {
        List<Project> projects = projectService.queryUnassignedProjectsL();
        log.info("ProjectServiceTest.queryUnassignedProjectsL: " + projects);
    }
}
