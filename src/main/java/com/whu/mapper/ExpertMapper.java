package com.whu.mapper;

import com.whu.pojo.Expert;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ExpertMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")

    /**
     * 根据专家id查询专家
     * @param id 专家id
     * @return 返回的专家
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "prizeList",
                    many = @Many(select = "com.whu.mapper.PrizeMapper.queryPrizesByExpertId",
                            fetchType = FetchType.EAGER))
    })
    @Select("SELECT * FROM t_expert WHERE id = #{id}")
    Expert queryExpertById(Long id);

    /**
     * 查询所有的专家
     * @return 返回的专家
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "prizeList",
                    many = @Many(select = "com.whu.mapper.PrizeMapper.queryPrizesByExpertId",
                            fetchType = FetchType.EAGER)),
            @Result(column = "id", property = "fProjectIdList",
                    many = @Many(select = "com.whu.mapper.ProjectAssignmentMapper.queryProjectIdsByExpertIdF",
                            fetchType = FetchType.EAGER)),
            @Result(column = "id", property = "lProjectIdList",
                    many = @Many(select = "com.whu.mapper.ProjectAssignmentMapper.queryProjectIdsByExpertIdL",
                            fetchType = FetchType.EAGER))
    })
    @Select("SELECT * FROM t_expert")
    List<Expert> queryAllExperts();


    /**
     * 根据专家用户名查询专家
     * @param username 专家用户名
     * @return 返回的专家
     */
    @Select("SELECT * FROM t_expert WHERE user_name = #{username}")
    Expert queryExpertByUsername(String username);

    /**
     * 更新专家信息
     * @param expert 更新后的专家
     * @return 事务执行结果：1：成功；0：失败
     */
    @Update("UPDATE t_expert SET " +
            "user_name = #{username}, " +
            "password = #{password}, " +
            "department = #{department}, " +
            "name = #{name}, " +
            "title = #{title} " +
            "WHERE id = #{id}")
    int updateExpert(Expert expert);
}
