package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EconoBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class EconoBenefitServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    EconoBenefitService econoBenefitService;

    @Test
    public void queryScoreByProjectId()
    {
        EconoBenefit econoBenefit = econoBenefitService.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EconoBenefitServiceTest.queryScoreByProjectId: " +  econoBenefit.toString());
    }

    @Test
    public void insertScore()
    {
        EconoBenefit  econoBenefit = new EconoBenefit();
        econoBenefit.setExpertId(2L);
        econoBenefit.setProjectId(4L);
        econoBenefit.setOperationPerformance(92);
        econoBenefit.setState(1);
        int code = econoBenefitService.insertScore(econoBenefit);
        log.info("EconoBenefitServiceTest.insertScore: " + code);
    }
}
