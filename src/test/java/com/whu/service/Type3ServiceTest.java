package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit3;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Type3ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    Type3Service type3Service;

    @Test
    public void insertType3Score()
    {
        EnvirBenefit3 envirBenefit3 = new EnvirBenefit3();
        envirBenefit3.setExpertId(2L);
        envirBenefit3.setProjectId(4L);
        envirBenefit3.setArt(95);
        envirBenefit3.setEnvirFriendliness(58);
        envirBenefit3.setProjectFunction(78);
        envirBenefit3.setProjectTechnology(85);
        envirBenefit3.setState(1);

        EconoBenefit  econoBenefit = new EconoBenefit();
        econoBenefit.setExpertId(2L);
        econoBenefit.setProjectId(4L);
        econoBenefit.setOperationPerformance(92);
        econoBenefit.setState(1);

        SocialBenefit socialBenefit = new SocialBenefit();
        socialBenefit.setExpertId(2L);
        socialBenefit.setProjectId(4L);
        socialBenefit.setEffect(87);
        socialBenefit.setState(1);
        float grade = 95.0f;
        int code = type3Service.insertType3Score(envirBenefit3,socialBenefit,econoBenefit,grade);
        log.info("Type3ServiceTest.insertType3Score: " + code);
    }

    @Test
    public void queryScoreByProjetIdAndState()
    {
        Map<String, Benefit> score = type3Service.queryScoreByProjectIdAndState(26L,1, 8L);
        if(score != null)
        {
            log.info("Type3ServiceTest.queryScoreByProjetIdAndState: " + 1);
        }
    }
}

