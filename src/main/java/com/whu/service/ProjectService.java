package com.whu.service;

import com.whu.pojo.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService
{
    /**
     * 根据id返回项目
     * @param id 项目的id
     * @return 返回的项目
     */
    Project queryProjectById(Long id);


    /**
     * 更新项目信息
     * @param project 更新后的项目信息
     * @return 事务操作就返回值
     */
    int updateProject(Project project);

    /**
     * 根据id数组返回对应的project数组
     * @param expertId 专家的id
     * @param state 初评还是会评
     * @return 返回的项目数组
     */
    List<Project> queryProjectsByExpertIdAndState(Long expertId, int state);

    /**
     * 查找表中所有的项目
     * @return 返回的项目列表
     */
    List<Project> queryAllProjects();

    /**
     * 查找初评未被分配项目
     * @return 返回的初评未被分配的项目
     */
    List<Project> queryUnassignedProjectsF();

    /**
     * 查找会评未被分配项目
     * @return 返回的会评未被分配的项目
     */
    List<Project> queryUnassignedProjectsL();

    /**
     * 从初评结果中选出来直接晋级和进入到会评的
     * @param projectIdListToNext  初评中进入会评的
     * @return
     */
    int selectProjectsAtFirstAssessment(List<Long> projectIdListToNext, List<Long> projectIdListToRemain);

    /**
     * 从会评结果中选出来直接晋级项目
     * @param projectIdListToPromotion 晋级的项目列表
     * @return
     */
    int selectProjectsAtLastAssessment(List<Long> projectIdListToPromotion, List<Long> projectIdListToRemain);
}
