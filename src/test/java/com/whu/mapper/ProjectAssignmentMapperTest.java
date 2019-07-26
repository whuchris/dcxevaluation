package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.ProjectAssignment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class ProjectAssignmentMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    @Test
    public void queryExpertIdByProjectIdAndState()
    {
        Long expertId =projectAssignmentMapper.queryExpertIdByProjectIdAndState(8L, 1, 27L);
        log.info("ProjectAssignmentMapperTest.queryExpertIdByProjectId: " + expertId);
    }

    @Test
    public void queryProjectIdsByExpertIdAndState()
    {
        List<Long> projectIdList = projectAssignmentMapper.queryProjectIdsByExpertIdAndState(1L,1);
        log.info("ProjectAssignmentMapperTest.queryProjectIdsByExpertIdAndState: " + projectIdList);
    }

    @Test
    public void insertAssignments()
    {
        List<Long> idList = new ArrayList<>();
        idList.add(6L);
        idList.add(7L);
        idList.add(8L);
        Long expertId = 2L;
        int state = 2;
        int code = projectAssignmentMapper.insertAssignments(state,idList,expertId);
        log.info("ProjectAssignmentMapperTest.insertAssignments: " + code);
    }

    @Test
    public void deleteAssignments()
    {
        List<Long> idList = new ArrayList<>();
        idList.add(8L);
        int state = 1;
        int code = projectAssignmentMapper.deleteAssignments(idList,state);
        log.info("ProjectAssignmentMapperTest.deleteAssignments: " + code);
    }
    
    @Test
    public void queryProjectIdsByExpertIdF()
    {
        List<Long> projectIdList = projectAssignmentMapper.queryProjectIdsByExpertIdF(1L);
        log.info("ProjectAssignmentMapperTest.queryProjectIdsByExpertIdF: " + projectIdList);
    }

    @Test
    public void queryProjectIdsByExpertIdL()
    {
        List<Long> projectIdList = projectAssignmentMapper.queryProjectIdsByExpertIdL(1L);
        log.info("ProjectAssignmentMapperTest.queryProjectIdsByExpertIdL: " + projectIdList);
    }

    @Test
    public void queryfExpertNameByProjectId()
    {
        List<String> name = projectAssignmentMapper.queryfExpertNameByProjectId(1L);
        log.info("ProjectAssignmentMapperTest.queryfExpertNameByProjectId: " + name);
    }

    @Test
    public void querylExpertNameByProjectId()
    {
        List<String> name = projectAssignmentMapper.querylExpertNameByProjectId(1L);
        log.info("ProjectAssignmentMapperTest.querylExpertNameByProjectId: " + name);
    }

    @Test
    public void queryAssignmentAndUpdate()
    {
        ProjectAssignment projectAssignment = projectAssignmentMapper.queryAssignmentByProjectIdAndExpertIdAndState
                        (79L,37L,1);
        log.info("ProjectAssignmentMapperTest.queryAssignment: " + projectAssignment);
        projectAssignment.setFinish(0);
        projectAssignmentMapper.updateAssignment(projectAssignment);
        projectAssignment = projectAssignmentMapper.queryAssignmentByProjectIdAndExpertIdAndState
                (79L,37L,1);
        log.info("ProjectAssignmentMapperTest.updateAssignment: " + projectAssignment);
    }
}
