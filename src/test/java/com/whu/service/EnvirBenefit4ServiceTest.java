package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit4;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EnvirBenefit4ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit4Service envirBenefit4Service;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit4 envirBenefit4 = envirBenefit4Service.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit4ServiceTest.queryScoreByProjectIdAndState: " + envirBenefit4);
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit4 envirBenefit4 = new EnvirBenefit4();
        envirBenefit4.setExpertId(2L);
        envirBenefit4.setProjectId(4L);
        envirBenefit4.setPhysicalEnvir(85);
        envirBenefit4.setDecorationTechnology(87);
        envirBenefit4.setDecorationMaterial(54);
        envirBenefit4.setCulturalEnvir(82);
        envirBenefit4.setState(1);
        int code = envirBenefit4Service.insertScore(envirBenefit4);
        log.info("EnvirBenefit4ServiceTest.insertScore: " + code);
    }
}
