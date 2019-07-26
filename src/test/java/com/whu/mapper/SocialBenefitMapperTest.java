package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit4;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SocialBenefitMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    SocialBenefitMapper socialBenefitMapper;

    @Test
    public void queryScoreByProjectId()
    {
        SocialBenefit socialBenefit = socialBenefitMapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("SocialBenefitMapperTest.queryScoreByProjectId: " + socialBenefit.toString());
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<SocialBenefit> econoBenefits = socialBenefitMapper.queryScoresByProjectIdAndState(26L,1);
        log.info("SocialBenefitMapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
    }

    @Test
    public void insertScore()
    {
        SocialBenefit socialBenefit = new SocialBenefit();
        socialBenefit.setExpertId(2L);
        socialBenefit.setProjectId(4L);
        socialBenefit.setEffect(87);
        socialBenefit.setState(1);
        int code = socialBenefitMapper.insertScore(socialBenefit);
        log.info("SocialBenefitMapperTest.insertScore: " + code);
    }
}
