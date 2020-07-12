package com.whu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExpertTypeMapper
{
    @Options(useGeneratedKeys = false, keyProperty = "id")

    @Insert("INSERT INTO t_expert_type(expert_id, prize_id, priority) " +
            "VALUES(#{expertId}, #{prizeId}, #{priority})")
    int insertExpertType(@Param(value = "expertId") Long expertId,
                         @Param(value = "prizeId") Long prizeId,
                         @Param(value = "priority") int priority);
}
