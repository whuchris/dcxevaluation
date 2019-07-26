package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Prize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PrizeServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    PrizeService prizeService;

    @Test
    public void queryPrizeById()
    {
        Prize prize = prizeService.queryPrizeById(2L);
        log.info("PrizeServiceTest.queryPrizeById: " + prize.toString());
    }
}
