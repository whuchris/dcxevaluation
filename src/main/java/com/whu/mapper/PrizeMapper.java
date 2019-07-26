package com.whu.mapper;

import com.whu.pojo.Prize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PrizeMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据奖项的id获取对应的奖项
     * @param id
     * @return 返回查找的奖项
     */
    @Select("SELECT * from t_prize WHERE id = #{id}")
    Prize queryPrizeById(Long id);


    /**
     * 根据专家id返回专家对应的评奖类型
     * @param expertId
     * @return 返回的评奖类型集合
     */
    @Select("SELECT * FROM  t_prize WHERE id IN" +
            "(SELECT prize_id FROM t_expert_type WHERE expert_id = #{expertId})")
    List<Prize> queryPrizesByExpertId(Long expertId);

    /**
     * 根据奖项种类返回奖项id
     * @param fType 7大奖项
     * @param sType 4种类型
     * @param tType 2种状态
     * @return 项目的id
     */
    @Select("SELECT id FROM t_prize WHERE f_type = #{fType} AND s_type = #{sType} AND t_type = #{tType}")
    Long queryPrizeIdByType(@Param(value = "fType") int fType, @Param(value = "sType")int sType, @Param(value = "tType")int tType);
}
