package com.whu.service;

import com.whu.pojo.Admin;

public interface AdminService
{
    /**
     * 根据管理员id返回管理员
     * @param id 管理员id
     * @return 返回的管理员
     */
    Admin queryAdminById(Long id);

    /**
     * 根据管理员用户名返回管理员
     * @param username 管理员用户名
     * @return 返回的管理员
     */
    Admin queryAdminByUsername(String username);

    /**
     * 查询管理员是否已经一键分配
     * @param id 管理员的id
     * @return 返回是否已经一键分配了
     */
    int queryStateById(Long id);

    /**
     * 获取初评一键分配信息
     * @return 是否已经一键初评
     */
    int getFirstState();

    /**
     * 获取会评一键分配信息
     * @return 是否已经一键会评
     */
    int getLastState();
}
