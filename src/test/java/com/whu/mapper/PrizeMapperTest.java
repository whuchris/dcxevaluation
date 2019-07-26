package com.whu.mapper;


import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Prize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PrizeMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    PrizeMapper prizeMapper;

    @Test
    public void queryPrizeById()
    {
        Prize prize = prizeMapper.queryPrizeById(48L);
        log.info("PrizeMapperTest.queryPrizeById: " + prize.toString());
    }

    @Test
    public void queryPrizesByExpertId()
    {
        List<Prize> prizeList = prizeMapper.queryPrizesByExpertId(1L);
        log.info("PrizeMapperTest.queryPrizesByExpertId: " + prizeList.toString());
    }

    @Test
    public void queryPrizeIdByType()
    {
        Long id = prizeMapper.queryPrizeIdByType(1,1,1);
        log.info("PrizeMapperTest.queryPrizeIdByType: " + id);
    }
}
