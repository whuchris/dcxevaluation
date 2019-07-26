package com.whu.service.impl;

import com.whu.mapper.EnvirBenefit2Mapper;
import com.whu.pojo.EnvirBenefit2;
import com.whu.pojo.EnvirBenefit4;
import com.whu.service.EnvirBenefit2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvirBenefit2ServiceImpl implements EnvirBenefit2Service
{
    @Autowired
    EnvirBenefit2Mapper envirBenefit2Mapper;

    @Override
    public EnvirBenefit2 queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        return envirBenefit2Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
    }

    @Override
    public List<EnvirBenefit2> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        return envirBenefit2Mapper.queryScoresByProjectIdAndState(projectId,state);
    }

    @Override
    public int insertScore(EnvirBenefit2 envirBenefit2)
    {
        return envirBenefit2Mapper.insertScore(envirBenefit2);
    }


}
