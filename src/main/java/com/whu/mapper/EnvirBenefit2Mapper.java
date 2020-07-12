package com.whu.mapper;

import com.whu.pojo.EnvirBenefit2;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnvirBenefit2Mapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @param expertId 专家id
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_2 WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "And expert_id = #{expertId}")
    EnvirBenefit2 queryScoreByProjectIdAndState(@Param("projectId") Long projectId,
                                                @Param("state")int state,
                                                @Param("expertId") Long expertId);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_2 WHERE project_id = #{projectId} " +
            "AND state = #{state}")
    List<EnvirBenefit2> queryScoresByProjectIdAndState(@Param("projectId") Long projectId,
                                                       @Param("state") int state);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_2 WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "ORDER BY expert_id")
    List<EnvirBenefit2> queryScoresByProjectIdAndStateOrderByExpertId(@Param("projectId") Long projectId,
                                                       @Param("state") int state);

    /**
     * 插入项目分数
     * @param envirBenefit2 插入的项目分数信息
     * @return 事务操作返回值
     */
    @Insert("INSERT INTO t_envir_benefit_2(project_id, expert_id, art, land_using, green_transportation, " +
            "envir, information_management, state) " +
            "VALUES(#{projectId}, #{expertId}, #{art}, #{landUsing}, #{greenTransportation}," +
            "#{envir},#{informationManagement}, #{state})")
    int insertScore(EnvirBenefit2 envirBenefit2);

    /**
     * 更新分数，只用于终评
     * @param envirBenefit2 更新的分数
     * @return
     */
    @Update("UPDATE t_envir_benefit_2 SET " +
            "art = #{art}, " +
            "land_using = #{landUsing}, " +
            "green_transportation = #{greenTransportation}, " +
            "envir = #{envir}, " +
            "information_management = #{informationManagement} " +
            "WHERE id = #{id}")
    int updateScore(EnvirBenefit2 envirBenefit2);

    /**
     * 删除评分,只用于终评
     * @param envirBenefit2
     * @return
     */
    @Delete("DELETE FROM t_envir_benefit_2 WHERE id = #{id}")
    int deleteScore(EnvirBenefit2 envirBenefit2);
}
