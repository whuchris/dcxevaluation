package com.whu.service.impl;

import com.whu.mapper.PrizeMapper;
import com.whu.pojo.Prize;
import com.whu.service.PrizeService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrizeServiceImpl implements PrizeService
{
    @Autowired
    PrizeMapper prizeMapper;

    @Override
    public Prize queryPrizeById(Long id)
    {
        return prizeMapper.queryPrizeById(id);
    }
}
