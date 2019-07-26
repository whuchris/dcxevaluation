package com.whu.service;

import com.whu.pojo.Expert;

import java.util.List;

public interface ExpertService
{
    /**
     * 根据id查找对应的评审专家
     * @param id 专家的id
     * @return 返回查询结果
     */
    Expert queryExpertById(Long id);

    /**
     * 根据用户名查找对应的评审专家
     * @param username 专家的用户名
     * @return 返回查询结果
     */
    Expert queryExpertByUsername(String username);

    /**
     * 更新专家信息
     * @param expert 更新后的专家信息
     * @return  事务返回结果
     */
    int updateExpert(Expert expert);

    /**
     * 查询所有的专家
     * @return 返回的专家
     */
    List<Expert> queryAllExperts();
}
