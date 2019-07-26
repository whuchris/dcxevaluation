package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.EnvirBenefit2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EnvirBenefit2MapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    EnvirBenefit2Mapper envirBenefit2Mapper;

    @Test
    public void queryScoreByProjectId()
    {
        EnvirBenefit2 envirBenefit2 = envirBenefit2Mapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EnvirBenefit2MapperTest.queryScoreByProjectIdAndState: " + envirBenefit2);
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<EnvirBenefit2> econoBenefits = envirBenefit2Mapper.queryScoresByProjectIdAndState(26L,1);
        log.info("EnvirBenefit2MapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
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
        int code = envirBenefit2Mapper.insertScore(envirBenefit2);
        log.info("EnvirBenefit2MapperTest.insertScore: " + code);
    }
}
