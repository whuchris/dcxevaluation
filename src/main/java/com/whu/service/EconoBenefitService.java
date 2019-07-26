package com.whu.service;

import com.whu.pojo.EconoBenefit;

import java.util.List;

public interface EconoBenefitService
{
    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    EconoBenefit queryScoreByProjectIdAndState(Long projectId, int state, Long expertId);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    List<EconoBenefit> queryScoresByProjectIdAndState(Long projectId, int state);

    /**
     * 插入项目分数
     * @param  econoBenefit 插入的项目分数信息
     * @return 事务操作返回值
     */
    int insertScore(EconoBenefit econoBenefit);
}
