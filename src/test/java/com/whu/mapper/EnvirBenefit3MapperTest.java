package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.EnvirBenefit3;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EnvirBenefit3MapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit3Mapper envirBenefit3Mapper;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit3 envirBenefit3 = envirBenefit3Mapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit3MapperTest.queryScoreByProjectIdAndState: " + envirBenefit3);
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<EnvirBenefit3> econoBenefits = envirBenefit3Mapper.queryScoresByProjectIdAndState(26L,1);
        log.info("EnvirBenefit3MapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit3 envirBenefit3 = new EnvirBenefit3();
        envirBenefit3.setExpertId(2L);
        envirBenefit3.setProjectId(4L);
        envirBenefit3.setArt(95);
        envirBenefit3.setEnvirFriendliness(58);
        envirBenefit3.setProjectFunction(78);
        envirBenefit3.setProjectTechnology(85);
        envirBenefit3.setState(1);
        int code = envirBenefit3Mapper.insertScore(envirBenefit3);
        log.info("EnvirBenefit3MapperTest.insertScore: " + code);
    }
}
