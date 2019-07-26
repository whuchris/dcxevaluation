package com.whu.mapper;

import com.whu.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")

    /**
     * 根据管理员id返回管理员
     * @param id 管理员id
     * @return 返回的专家
     */
    @Select("SELECT * FROM t_admin WHERE id = #{id}")
    Admin queryAdminById(Long id);


    /**
     * 根据管理员用户名返回管理员
     * @param username 管理员用户名
     * @return 返回的专家
     */
    @Select("SELECT * FROM t_admin WHERE user_name = #{username}")
    Admin queryAdminByUsername(String username);

    /**
     * 查询管理员是否已经一键分配
     * @param id 管理员的id
     * @return 返回是否已经一键分配了
     */
    @Select("SELECT auto_assign_1 FROM t_admin WHERE id = #{id}")
    int queryFirstStateById(Long id);

    /**
     * 初评一键分配之后
     * @return 事务执行状态码
     */
    @Update("UPDATE t_admin SET auto_assign_1 = 1")
    int changeStateAtFirstAssessment();

    /**
     * 会评一键分配之后
     * @return
     */
    @Update("UPDATE t_admin SET auto_assign_2 = 1")
    int changeStateAtLastAssessment();

    /**
     * 获取初评一键分配信息
     * @return 是否已经一键初评
     */
    @Select("SELECT auto_assign_1 FROM t_admin WHERE id = 1")
    int getFirstState();

    /**
     * 获取会评一键分配信息
     * @return 是否已经一键会评
     */
    @Select("SELECT auto_assign_2 FROM t_admin WHERE id = 1")
    int getLastState();
}
