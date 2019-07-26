package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit2;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Type2ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    Type2Service type2Service;

    @Test
    public void insertType2Score()
    {
        EnvirBenefit2 envirBenefit2 = new EnvirBenefit2();
        envirBenefit2.setExpertId(2L);
        envirBenefit2.setProjectId(4L);
        envirBenefit2.setArt(95);
        envirBenefit2.setLandUsing(92);
        envirBenefit2.setInformationManagement(89);
        envirBenefit2.setEnvir(84);
        envirBenefit2.setGreenTransportation(79);
        envirBenefit2.setState(1);

        EconoBenefit econoBenefit = new EconoBenefit();
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
        int code = type2Service.insertType2Score(envirBenefit2,socialBenefit,econoBenefit,grade);
        log.info("Type2ServiceTest.insertType2Score: " + code);
    }

    @Test
    public void queryScoreByProjetIdAndState()
    {
        Map<String, Benefit> score = type2Service.queryScoreByProjectIdAndState(26L,1, 8L);
        if(score == null)
        {
            log.info("Type2ServiceTest.queryScoreByProjetIdAndState: " + 1);
        }
    }
}
