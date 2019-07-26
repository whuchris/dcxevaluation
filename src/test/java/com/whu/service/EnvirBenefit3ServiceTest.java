package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit3;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EnvirBenefit3ServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit3Service envirBenefit3Service;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit3 envirBenefit3 = envirBenefit3Service.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit3ServiceTest.queryScoreByProjectIdAndState: " + envirBenefit3);
    }

    @Test
    public void insertScore()
    {
        EnvirBenefit3 envirBenefit3 = new EnvirBenefit3();
        envirBenefit3.setExpertId(2L);
        envirBenefit3.setProjectId(4L);
        envirBenefit3.setArt(95);
        envirBenefit3.setEnvirFriendliness(58);
        envirBenefit3.setProjectFunction(78);
        envirBenefit3.setProjectTechnology(85);
        envirBenefit3.setState(1);
        int code = envirBenefit3Service.insertScore(envirBenefit3);
        log.info("EnvirBenefit3ServiceTest.insertScore: " + code);
    }
}
