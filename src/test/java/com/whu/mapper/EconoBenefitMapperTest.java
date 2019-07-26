package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.SocialBenefit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EconoBenefitMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    EconoBenefitMapper econoBenefitMapper;

    @Test
    public void queryScoreByProjectId()
    {
        EconoBenefit econoBenefit = econoBenefitMapper.queryScoreByProjectIdAndState(26L,1, 8L);
        log.info("EconoBenefitMapperTest.queryScoreByProjectId: " +  econoBenefit.toString());
    }

    @Test
    public void queryScoresByProjectId()
    {
        List<EconoBenefit> econoBenefits = econoBenefitMapper.queryScoresByProjectIdAndState(26L,1);
        log.info("EconoBenefitMapperTest.queryScoresByProjectId: " +  econoBenefits.toString());
    }

    @Test
    public void insertScore()
    {
        EconoBenefit  econoBenefit = new EconoBenefit();
        econoBenefit.setExpertId(2L);
        econoBenefit.setProjectId(4L);
        econoBenefit.setOperationPerformance(92);
        econoBenefit.setState(1);
        int code = econoBenefitMapper.insertScore(econoBenefit);
        log.info("SocialBenefitMapperTest.insertScore: " + code);
    }
}
