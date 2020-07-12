package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Expert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ExpertMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    ExpertMapper expertMapper;

    @Test
    public void queryExpertById()
    {
        Expert expert = expertMapper.queryExpertById(1L);
        log.info("ExpertMapperTest.queryExpertById :" + expert.toString());
    }

    @Test
    public void queryExpertByUsername()
    {
        Expert expert = expertMapper.queryExpertByUsername("test2");
        log.info("ExpertMapperTest.queryExpertByUsername :" + expert.toString());
    }

    @Test
    public void updateExpert()
    {
        Expert expert = new Expert();
        expert.setId(1L);
        expert.setName("test2");
        expert.setDepartment("test2");
        expert.setPassword("test2");
        expert.setTelephone("test2");
        expert.setTitle("test2");
        expert.setUsername("test2");
        int code = expertMapper.updateExpert(expert);
        log.info("ExpertMapperTest.updateExpert :" + code);
    }

    @Test
    public void queryAllExperts()
    {
        List<Expert> experts = new ArrayList<>();
        experts = expertMapper.queryAllExperts();
        log.info("ExpertMapperTest.queryAllExperts :" + experts);
    }

    @Test
    public void queryExpertByName()
    {
        String name = "戴春";
        Expert expert = expertMapper.queryExpertByName(name);
        log.info("ExpertMapperTest.queryExpertByName :" + expert);
    }
}
