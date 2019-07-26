package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Type1ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    Type1Service type1Service;

    @Test
    public void insertType1Score()
    {
        EconoBenefit econoBenefit = new EconoBenefit();
        econoBenefit.setExpertId(2L);
        econoBenefit.setProjectId(4L);
        econoBenefit.setOperationPerformance(92);
        econoBenefit.setState(1);

        EnvirBenefit1 envirBenefit1 = new EnvirBenefit1();
        envirBenefit1.setExpertId(2L);
        envirBenefit1.setProjectId(4L);
        envirBenefit1.setArt(95);
        envirBenefit1.setOutdoorEnvir(26);
        envirBenefit1.setConstructionManagement(92);
        envirBenefit1.setInnovationEvaluation(88);
        envirBenefit1.setOperationManagement(82);
        envirBenefit1.setState(1);

        SocialBenefit socialBenefit = new SocialBenefit();
        socialBenefit.setId(1L);
        socialBenefit.setExpertId(2L);
        socialBenefit.setProjectId(4L);
        socialBenefit.setEffect(87);
        socialBenefit.setState(1);

        float grade = 95.0f;

        int code = type1Service.insertType1Score(envirBenefit1,socialBenefit,econoBenefit,grade);
        log.info("Type1ServiceTest.insertType1Score: " + code);
    }

    @Test
    public void queryScoreByProjetIdAndState()
    {
        Map<String, Benefit> score = type1Service.queryScoreByProjectIdAndState(26L,1, 8L);
        if(score == null)
        {
            log.info("Type1ServiceTest.queryScoreByProjetIdAndState: " + 1);
        }
    }
}
