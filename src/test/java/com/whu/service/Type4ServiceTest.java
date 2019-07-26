package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit4;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Type4ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    Type4Service type4Service;

    @Test
    public void insertType4Score()
    {

        EnvirBenefit4 envirBenefit4= new EnvirBenefit4();
        envirBenefit4.setExpertId(2L);
        envirBenefit4.setProjectId(4L);
        envirBenefit4.setCulturalEnvir(75);
        envirBenefit4.setDecorationMaterial(28);
        envirBenefit4.setDecorationTechnology(74);
        envirBenefit4.setPhysicalEnvir(59);
        envirBenefit4.setState(1);

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
        int code = type4Service.insertType4Score(envirBenefit4,socialBenefit,econoBenefit,grade);
        log.info("Type4ServiceTest.insertType4Score: " + code);
    }

    @Test
    public void queryScoreByProjetIdAndState()
    {
        Map<String, Benefit> score = type4Service.queryScoreByProjectIdAndState(26L,1, 8L);
        if(score != null)
        {
            log.info("Type4ServiceTest.queryScoreByProjetIdAndState: " + 1);
        }
    }
}
