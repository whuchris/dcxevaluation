package com.whu.mapper;

import com.whu.pojo.EconoBenefit;
import com.whu.pojo.SocialBenefit;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EconoBenefitMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_econo_benefit WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "AND expert_id = #{expertId}")
    EconoBenefit queryScoreByProjectIdAndState(@Param("projectId") Long projectId,
                                               @Param("state") int state,
                                               @Param("expertId") Long expertId);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_econo_benefit WHERE project_id = #{projectId} " +
            "AND state = #{state}")
    List<EconoBenefit> queryScoresByProjectIdAndState(@Param("projectId") Long projectId,
                                                      @Param("state") int state);

    /**
     * 插入项目分数
     * @param  econoBenefit 插入的项目分数信息
     * @return 事务操作返回值
     */
    @Insert("INSERT INTO t_econo_benefit(project_id, expert_id, operation_performance, state) " +
            "VALUES(#{projectId}, #{expertId}, #{operationPerformance}, #{state})")
    int insertScore(EconoBenefit econoBenefit);
}
