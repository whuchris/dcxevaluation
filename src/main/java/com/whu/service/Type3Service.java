package com.whu.service;

import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit3;
import com.whu.pojo.SocialBenefit;

import java.util.List;
import java.util.Map;

public interface Type3Service
{
    int insertType3Score(EnvirBenefit3 envirBenefit3, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                         float grade);

    Map<String, Benefit> queryScoreByProjectIdAndState(Long projectId, int state, Long expertId);

    Map<String, Float> queryScoresByProjectIdAndState(Long projectId, int state);
}
