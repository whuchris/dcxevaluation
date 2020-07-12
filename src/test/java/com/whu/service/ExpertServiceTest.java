package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Expert;
import com.whu.pojo.Project;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class ExpertServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    ExpertService expertService;

    @Test
    public void queryExpertById()
    {
        Expert expert = expertService.queryExpertById(1L);
        log.info("ExpertServiceTest.queryExpertById: " + expert.toString());
    }

    @Test
    public void queryExpertByUsername()
    {
        Expert expert = expertService.queryExpertByUsername("test3");
        log.info("ExpertServiceTest.queryExpertByUsername: " + expert.toString());
    }

    @Test
    public void updateExpert()
    {
        Expert expert = new Expert();
        expert.setId(1L);
        expert.setName("test3");
        expert.setDepartment("test3");
        expert.setPassword("test3");
        expert.setTelephone("test3");
        expert.setTitle("test3");
        expert.setUsername("test3");
        int code = expertService.updateExpert(expert);
        log.info("ExpertServiceTest.updateExpert: " + code);
    }

    @Test
    public void queryAllExperts()
    {
        List<Expert> experts = expertService.queryAllExperts();
        log.info("ExpertServiceTest.queryAllExperts :" + experts);
    }
}
