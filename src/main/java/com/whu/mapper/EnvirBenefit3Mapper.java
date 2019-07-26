package com.whu.mapper;

import com.whu.pojo.EnvirBenefit3;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EnvirBenefit3Mapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @param expertId 专家id
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_3 WHERE project_id = #{projectId} " +
            "AND state = #{state} " +
            "And expert_id = #{expertId}")
    EnvirBenefit3 queryScoreByProjectIdAndState(@Param("projectId") Long projectId,
                                                @Param("state")int state,
                                                @Param("expertId") Long expertId);

    /**
     * 根据项目的id和项目的评分进度返回项目评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回的评分情况
     */
    @Select("SELECT * FROM t_envir_benefit_3 WHERE project_id = #{projectId} " +
            "AND state = #{state}")
    List<EnvirBenefit3> queryScoresByProjectIdAndState(@Param("projectId") Long projectId,
                                                       @Param("state") int state);

    /**
     * 插入项目分数
     * @param envirBenefit3 插入的项目分数信息
     * @return 事务操作返回值
     */
    @Insert("INSERT INTO t_envir_benefit_3(project_id, expert_id, art, project_function, project_technology, " +
            "envir_friendliness, state) " +
            "VALUES(#{projectId}, #{expertId}, #{art}, #{projectFunction}, #{projectTechnology}," +
            "#{envirFriendliness}, #{state})")
    int insertScore(EnvirBenefit3 envirBenefit3);
}
