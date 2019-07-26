package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit1;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EnvirBenefit1ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit1Service envirBenefit1Service;

    @Test
    public void querySoceByProjectId()
    {
        EnvirBenefit1 envirBenefit1 = envirBenefit1Service.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info(" EnvirBenefit1ServiceTest.querySoceByProjectIdAndState: " + envirBenefit1.toString());
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit1 envirBenefit1 = new EnvirBenefit1();
        envirBenefit1.setExpertId(2L);
        envirBenefit1.setProjectId(4L);
        envirBenefit1.setArt(95);
        envirBenefit1.setOutdoorEnvir(26);
        envirBenefit1.setResourceUtilization(85);
        envirBenefit1.setIndoorEnvir(79);
        envirBenefit1.setConstructionManagement(92);
        envirBenefit1.setInnovationEvaluation(88);
        envirBenefit1.setOperationManagement(82);
        envirBenefit1.setState(1);
        int code = envirBenefit1Service.insertScore(envirBenefit1);
        log.info(" EnvirBenefit1ServiceTest.insertScore: " + code);
    }
}
