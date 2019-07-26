package com.whu.service;

import com.whu.pojo.*;

import java.util.List;
import java.util.Map;

public interface Type2Service
{
    int insertType2Score(EnvirBenefit2 envirBenefit2, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                         float grade);

    Map<String, Benefit> queryScoreByProjectIdAndState(Long projectId, int state, Long expertId);

    Map<String, Float> queryScoresByProjectIdAndState(Long projectId, int state);
}
