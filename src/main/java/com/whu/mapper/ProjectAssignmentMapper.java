package com.whu.mapper;

import com.whu.pojo.ProjectAssignment;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProjectAssignmentMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 返回评审该项目的专家id
     * @param projectId 当前的项目id
     * @return 返回评审该项目的专家id
     */
    @Select("SELECT expert_id FROM t_project_assignment " +
            "WHERE project_id = #{projectId} AND state = #{state} AND expert_id = #{expertId}")
    Long queryExpertIdByProjectIdAndState(@Param(value = "projectId") Long projectId,
                                          @Param(value = "state")int state,
                                          @Param(value = "expertId") Long expertId);

    /**
     * 根据专家id和状态返回专家评审的所有项目id
     * @param expertId 专家的id
     * @param state 对应的状态，初评或者会评
     * @return 返回专家评审的项目id数组
     */
    @Select("SELECT project_id FROM t_project_assignment " +
            "WHERE expert_id =#{expertId} AND state = #{state}")
    List<Long> queryProjectIdsByExpertIdAndState(@Param(value = "expertId")Long expertId,
                                                 @Param(value = "state")int state);

    @Select("SELECT finish FROM t_project_assignment " +
            "WHERE expert_id =#{expertId} AND state = #{state}")
    List<Integer> queryFinishByExpertIdAndState(@Param(value = "expertId")Long expertId,
                                                 @Param(value = "state")int state);

    @Select("SELECT project_id FROM t_project_assignment " +
            "WHERE expert_id =#{expertId} AND state = 1")
    List<Long> queryProjectIdsByExpertIdF(@Param(value = "expertId")Long expertId);

    @Select("SELECT project_id FROM t_project_assignment " +
            "WHERE expert_id =#{expertId} AND state = 2")
    List<Long> queryProjectIdsByExpertIdL(@Param(value = "expertId")Long expertId);

    /**
     * 批量分配项目
     * @param state 分配类型，初评还是会评
     * @param projectIdList  需要分配的项目集合
     * @param expertId 分配给的专家id
     * @return 操作状态码
     */
    @Insert(
            {
                    "<script>",
                    "INSERT INTO t_project_assignment (project_id, expert_id, state)",
                    "VALUES",
                    "<foreach collection = 'projectIdList' index = 'index' item = 'projectId' separator = ','>",
                    "(#{projectId}, #{expertId}, #{state})",
                    "</foreach>",
                    "</script>"
            }
    )
    int insertAssignments(@Param(value = "state") int state,
                          @Param(value = "projectIdList") List<Long> projectIdList,
                          @Param(value = "expertId") Long expertId);

    /*
    @Insert(
            {
                    "<script>",
                    "INSERT INTO t_project_assignment (project_id, expert_id, state)",
                    "VALUES",
                    "<foreach collection = 'expertIdList' index = 'index' item = 'expertId' separator = ','>",
                    "(#{projectId}, #{expertId}, #{state})",
                    "</foreach>",
                    "</script>"
            }
    )
    int insertAssignments(@Param(value = "state") int state,
                          @Param(value = "projectId") Long projectId,
                          @Param(value = "expertIdList") List<Long> expertIdList);*/

    @Select("SELECT name FROM t_expert WHERE id IN " +
            "(SELECT expert_id FROM t_project_assignment WHERE project_id = #{projectId} AND state = 1)")
    List<String> queryfExpertNameByProjectId(Long projectId);

    @Select("SELECT name FROM t_expert WHERE id IN " +
            "(SELECT expert_id FROM t_project_assignment WHERE project_id = #{projectId} AND state = 2)")
    List<String> querylExpertNameByProjectId(Long projectId);

    @Delete
    ({
            "<script>",
            "DELETE FROM t_project_assignment",
            "<where> ",
            "project_id IN ",
            "<foreach collection='projectIdList' index='index' item='projectId' open='(' separator=',' close=')'>",
            "#{projectId} ",
            "</foreach>",
            "AND state = ",
            "#{state}",
            "</where>",
            "</script>"
    })
    int deleteAssignments(@Param(value = "projectIdList") List<Long> projectIdList,
                          @Param(value = "state") int state);

    /**
     * 撤回分配的信息
     * @param projectId 撤回分配的项目id
     * @param expertId  撤回分配的专家id
     * @param state 初评或者会评状态
     * @return
     */
    @Delete("DELETE FROM t_project_assignment WHERE project_id = #{projectId} " +
            "AND expert_id = #{expertId} AND state = #{state}")
    int deleteAssignment(@Param(value = "projectId") Long projectId,
                         @Param(value = "expertId") Long expertId,
                         @Param(value = "state") int state);

    /**
     * 更新项目分配的信息，主要更新是否已经评分
     * @param projectAssignment 更新后的项目分配信息
     * @return  返回项目的分数
     */
    @Update("UPDATE t_project_assignment SET " +
            "project_id = #{projectId}, " +
            "expert_id = #{expertId}, " +
            "finish = #{finish}, " +
            "grade = #{grade} " +
            "WHERE id = #{id}")
    int updateAssignment(ProjectAssignment projectAssignment);

    /**
     * 根据项目id，专家id和状态返回项目评分情况
     * @param projectId 项目id
     * @param expertId 专家id
     * @param state 初评或者会评
     * @return 返回分配的详细信息
     */
    @Select("SELECT * FROM t_project_assignment WHERE " +
            "project_id = #{projectId} AND " +
            "expert_id = #{expertId} AND " +
            "state = #{state}")
    ProjectAssignment queryAssignmentByProjectIdAndExpertIdAndState(@Param(value = "projectId") Long projectId,
                                                                    @Param(value = "expertId") Long expertId,
                                                                    @Param(value = "state") int state);

    /**
     * 根据项目id，状态返回项目所有评分情况
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回分配的详细信息
     */
    @Select("SELECT * FROM t_project_assignment WHERE " +
            "project_id = #{projectId} AND " +
            "state = #{state} ")
    List<ProjectAssignment> queryAssignmentsByProjectIdAndState(@Param(value = "projectId") Long projectId,
                                                                @Param(value = "state") int state);

    /**
     * 根据项目id，状态返回项目所有评分情况，并按照分数大小排序
     * @param projectId 项目id
     * @param state 初评或者会评
     * @return 返回分配的详细信息
     */
    @Select("SELECT * FROM t_project_assignment WHERE " +
            "project_id = #{projectId} AND " +
            "state = #{state} " +
            "ORDER BY grade")
    List<ProjectAssignment> queryAssignmentsByProjectIdAndStateOrderByGrade(@Param(value = "projectId") Long projectId,
                                                                @Param(value = "state") int state);

    @Select("SELECT finish FROM t_project_assignment WHERE " +
            "project_id = #{projectId} AND " +
            "expert_id = #{expertId} AND " +
            "state = #{state}")
    int queryFinishByProjectIdAndExpertIdAndState(@Param(value = "projectId") Long projectId,
                                                  @Param(value = "expertId") Long expertId,
                                                  @Param(value = "state") int state);


    /**
     * 插入一条记录，用于终评
     * @param projectAssignment 插入的终评记录
     * @return
     */
    @Insert("INSERT INTO t_project_assignment (project_id, expert_id, state, finish, grade) " +
        "VALUES(#{projectId}, #{expertId}, #{state}, #{finish}, #{grade})")
    int insertProjectAssignment(ProjectAssignment projectAssignment);
}
