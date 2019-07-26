package com.whu.service.impl;

import com.whu.mapper.EnvirBenefit4Mapper;
import com.whu.pojo.EnvirBenefit4;
import com.whu.service.EnvirBenefit4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvirBenefit4ServiceImpl implements EnvirBenefit4Service
{
    @Autowired
    EnvirBenefit4Mapper envirBenefit4Mapper;

    @Override
    public EnvirBenefit4 queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        return envirBenefit4Mapper.queryScoreByProjectIdAndState(projectId, state, expertId);
    }

    @Override
    public List<EnvirBenefit4> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        return envirBenefit4Mapper.queryScoresByProjectIdAndState(projectId,state);
    }

    @Override
    public int insertScore(EnvirBenefit4 envirBenefit4)
    {
        return envirBenefit4Mapper.insertScore(envirBenefit4);
    }
}
