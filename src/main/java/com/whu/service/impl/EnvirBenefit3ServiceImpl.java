package com.whu.service.impl;


import com.whu.mapper.EnvirBenefit3Mapper;
import com.whu.pojo.EnvirBenefit3;
import com.whu.pojo.EnvirBenefit4;
import com.whu.service.EnvirBenefit3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvirBenefit3ServiceImpl implements EnvirBenefit3Service
{
    @Autowired
    EnvirBenefit3Mapper envirBenefit3Mapper;

    @Override
    public EnvirBenefit3 queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        return envirBenefit3Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
    }

    @Override
    public List<EnvirBenefit3> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        return envirBenefit3Mapper.queryScoresByProjectIdAndState(projectId,state);
    }

    @Override
    public int insertScore(EnvirBenefit3 envirBenefit3)
    {
        return envirBenefit3Mapper.insertScore(envirBenefit3);
    }


}