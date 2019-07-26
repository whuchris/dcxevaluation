package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EnvirBenefit2ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit2Service envirBenefit2Service;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit2 envirBenefit2 = envirBenefit2Service.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit2ServiceTest.queryScoreByProjectIdAndState: " + envirBenefit2);
    }

    @Test
    public void insertScore()
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
        int code = envirBenefit2Service.insertScore(envirBenefit2);
        log.info("EnvirBenefit2ServiceTest.insertScore: " + code);
    }
}
