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
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_econo_benefit WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "ORDER BY expert_id")
    List<EconoBenefit> queryScoresByProjectIdAndStateOrderByExpertId(@Param("projectId") Long projectId,
                                                      @Param("state") int state);

    /**
     * 插入项目分数
     * @param  econoBenefit 插入的项目分数信息
     * @return 事务操作返回值
     */
    @Insert("INSERT INTO t_econo_benefit(project_id, expert_id, operation_performance, state) " +
            "VALUES(#{projectId}, #{expertId}, #{operationPerformance}, #{state})")
    int insertScore(EconoBenefit econoBenefit);

    /**
     * 更新分数，只用于终评
     * @param econoBenefit 更新的分数
     * @return
     */
    @Update("UPDATE t_econo_benefit SET operation_performance = #{operationPerformance} " +
            "WHERE id = #{id}")
    int updateScore(EconoBenefit econoBenefit);

    /**
     * 删除评分,只用于终评
     * @param econoBenefit
     * @return
     */
    @Delete("DELETE FROM t_econo_benefit WHERE id = #{id}")
    int deleteScore(EconoBenefit econoBenefit);
}
