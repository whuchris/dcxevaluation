package com.whu.service.impl;

import com.whu.mapper.EnvirBenefit1Mapper;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.EnvirBenefit4;
import com.whu.service.EnvirBenefit1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvirBenefit1ServiceImpl implements EnvirBenefit1Service
{
    @Autowired
    EnvirBenefit1Mapper envirBenefit1Mapper;

    @Override
    public EnvirBenefit1 queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        return envirBenefit1Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
    }

    @Override
    public List<EnvirBenefit1> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        return envirBenefit1Mapper.queryScoresByProjectIdAndState(projectId,state);
    }

    @Override
    public int insertScore(EnvirBenefit1 envirBenefit1)
    {
        return envirBenefit1Mapper.insertScore(envirBenefit1);
    }
}
