package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SocialBenefitServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    SocialBenefitService socialBenefitService;

    @Test
    public void queryScoreByProjectIdAndState()
    {
        SocialBenefit socialBenefit =  socialBenefitService.queryScoreByProjectIdAndState(26L,1, 8L);
        if(socialBenefit != null)
            log.info("SocialBenefitServiceTest.queryScoreByProjectIdAndState: " + socialBenefit.toString());
    }

    @Test
    public void insertScore()
    {
        SocialBenefit socialBenefit = new SocialBenefit();
        socialBenefit.setExpertId(2L);
        socialBenefit.setProjectId(4L);
        socialBenefit.setEffect(87);
        socialBenefit.setState(1);
        int code = socialBenefitService.insertScore(socialBenefit);
        log.info("SocialBenefitServiceTest.insertScore: " + code);
    }
}
