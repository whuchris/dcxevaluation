package com.whu.service.impl;

import com.whu.mapper.EconoBenefitMapper;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit4;
import com.whu.service.EconoBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EconoBenefitServiceImpl implements EconoBenefitService
{
    @Autowired
    EconoBenefitMapper econoBenefitMapper;

    @Override
    public EconoBenefit queryScoreByProjectIdAndState(Long projectId, int state, Long expertId)
    {
        return econoBenefitMapper.queryScoreByProjectIdAndState(projectId, state, expertId);
    }
    @Override
    public List<EconoBenefit> queryScoresByProjectIdAndState(Long projectId, int state)
    {
        return econoBenefitMapper.queryScoresByProjectIdAndState(projectId,state);
    }

    @Override
    public int insertScore(EconoBenefit econoBenefit)
    {
        return econoBenefitMapper.insertScore(econoBenefit);
    }
}
