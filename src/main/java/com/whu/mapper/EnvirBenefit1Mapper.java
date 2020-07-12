package com.whu.mapper;

import com.whu.pojo.EconoBenefit;
import com.whu.pojo.EnvirBenefit1;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnvirBenefit1Mapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @param expertId 专家id
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_1 WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "And expert_id = #{expertId}")
    EnvirBenefit1 queryScoreByProjectIdAndState(@Param("projectId") Long projectId,
                                                @Param("state")int state,
                                                @Param("expertId") Long expertId);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_1 WHERE project_id = #{projectId} " +
            "AND state = #{state}")
    List<EnvirBenefit1> queryScoresByProjectIdAndState(@Param("projectId") Long projectId,
                                                      @Param("state") int state);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_1 WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "ORDER BY expert_id")
    List<EnvirBenefit1> queryScoresByProjectIdAndStateOrderByExpertId(@Param("projectId") Long projectId,
                                                       @Param("state") int state);

    /**
     * 插入项目分数
     * @param envirBenefit1 插入的项目分数信息
     * @return 事务操作返回值
     */
    @Insert("INSERT INTO t_envir_benefit_1(project_id, expert_id, art, outdoor_envir, resource_utilization, " +
            "indoor_envir, construction_management, operation_management, innovation_evaluation, state) " +
            "VALUES(#{projectId}, #{expertId}, #{art}, #{outdoorEnvir}, #{resourceUtilization}," +
            "#{indoorEnvir},#{constructionManagement}, #{operationManagement}, #{innovationEvaluation}, #{state})")
    int insertScore(EnvirBenefit1 envirBenefit1);

    /**
     * 更新分数，只用于终评
     * @param envirBenefit1 更新的分数
     * @return
     */
    @Update("UPDATE t_envir_benefit_1 SET " +
            "art = #{art}, " +
            "outdoor_envir = #{outdoorEnvir}, " +
            "resource_utilization = #{resourceUtilization}, " +
            "indoor_envir = #{outdoorEnvir}, " +
            "construction_management = #{constructionManagement}, " +
            "operation_management = #{operationManagement}, " +
            "innovation_evaluation = #{innovationEvaluation} " +
            "WHERE id = #{id}")
    int updateScore(EnvirBenefit1 envirBenefit1);

    /**
     * 删除评分,只用于终评
     * @param envirBenefit1
     * @return
     */
    @Delete("DELETE FROM t_envir_benefit_1 WHERE id = #{id}")
    int deleteScore(EnvirBenefit1 envirBenefit1);
}
