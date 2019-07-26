package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.EnvirBenefit4;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EnvirBenefit4MapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit4Mapper envirBenefit4Mapper;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit4 envirBenefit4 = envirBenefit4Mapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit4MapperTest.queryScoreByProjectIdAndState: " + envirBenefit4);
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<EnvirBenefit4> econoBenefits = envirBenefit4Mapper.queryScoresByProjectIdAndState(26L,1);
        log.info("EnvirBenefit4MapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit4 envirBenefit4= new EnvirBenefit4();
        envirBenefit4.setExpertId(2L);
        envirBenefit4.setProjectId(4L);
        envirBenefit4.setCulturalEnvir(75);
        envirBenefit4.setDecorationMaterial(28);
        envirBenefit4.setDecorationTechnology(74);
        envirBenefit4.setPhysicalEnvir(59);
        envirBenefit4.setState(1);
        int code = envirBenefit4Mapper.insertScore(envirBenefit4);
        log.info("EnvirBenefit4MapperTest.insertScore: " + code);
    }
}

