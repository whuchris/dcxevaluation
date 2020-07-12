package com.whu.service;

import com.whu.pojo.Benefit;
import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit1;
import com.whu.pojo.SocialBenefit;

import java.util.List;
import java.util.Map;

public interface Type1Service
{
    /**
     * 插入到建筑类的分数，其他几项同理
     * @param envirBenefit1
     * @param socialBenefit
     * @param econoBenefit
     * @param grade
     * @return
     */
    int insertType1Score(EnvirBenefit1 envirBenefit1, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                         float grade);

    /**
     * 改变建筑类分数，只适用于终评
     * @param envirBenefit1
     * @param socialBenefit
     * @param econoBenefit
     * @param grade
     * @return
     */
    int alterType1Score(EnvirBenefit1 envirBenefit1, SocialBenefit socialBenefit, EconoBenefit econoBenefit,
                        float grade);

    Map<String, Benefit> queryScoreByProjectIdAndState(Long projectId, int state, Long expertId);

    Map<String, Float> queryScoresByProjectIdAndState(Long projectId, int state);
}
