package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit1;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EnvirBenefit1MapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit1Mapper envirBenefit1Mapper;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit1 envirBenefit1 = envirBenefit1Mapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit1MapperTest.queryScoreByProjectIdAndState: " + envirBenefit1);
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<EnvirBenefit1> econoBenefits = envirBenefit1Mapper.queryScoresByProjectIdAndState(26L,1);
        log.info("EnvirBenefit1MapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit1 envirBenefit1 = new EnvirBenefit1();
        envirBenefit1.setExpertId(2L);
        envirBenefit1.setProjectId(4L);
        envirBenefit1.setArt(95.8f);
        envirBenefit1.setOutdoorEnvir(26.5f);
        envirBenefit1.setResourceUtilization(85.5f);
        envirBenefit1.setIndoorEnvir(79.6f);
        envirBenefit1.setConstructionManagement(92.2f);
        envirBenefit1.setInnovationEvaluation(88.2f);
        envirBenefit1.setOperationManagement(82.8f);
        envirBenefit1.setState(1);
        int code = envirBenefit1Mapper.insertScore(envirBenefit1);
        log.info("EnvirBenefit1MapperTest.insertScore: " + code);
    }
}
