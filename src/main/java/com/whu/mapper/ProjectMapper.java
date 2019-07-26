package com.whu.mapper;

import com.whu.pojo.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
public interface ProjectMapper
{
    @Options(useGeneratedKeys = true, keyProperty = "id")
    /**
     * 根据项目的id查找项目
     * @param id 项目的id
     * @return 返回查找到的项目
     */
    @Results(@Result(column = "prize_id", property = "prize", one = @One(select = "com.whu.mapper.PrizeMapper.queryPrizeById")))
    @Select("SELECT * FROM t_project WHERE id = #{id}")
    Project queryProjectById(Long id);

    /**
     * 更新项目信息
     * @param project 更新后的项目
     * @return 事务操作返回值
     */
    @Update("UPDATE t_project SET " +
            "prize_class = #{prizeClass}, " +
            "application_time = #{applicationTime}, " +
            "f_grade = #{fGrade}, " +
            "l_grade = #{lGrade}, " +
            "assessment_state = #{assessmentState} WHERE id = #{id}")
    int updateProject(Project project);


    /**
     * 根据id数组返回对应的project数组
     * @param projectIdList id数组
     * @return 返回的项目数组
     */
    @Select(
            {
                    "<script>",
                    "SELECT *, #{expertId} as expertId, #{state} as state FROM t_project",
                    "<where>",
                    "id IN",
                    "<foreach collection = 'projectIdList' item = 'id' index = 'index' " +
                            "open = '(' separator = ',' close = ')'>",
                    "#{id}",
                    "</foreach>",
                    "</where>",
                    "</script>"
            }
    )
    @Results({
            @Result(column = "prize_id", property = "prize",
                    one = @One(select = "com.whu.mapper.PrizeMapper.queryPrizeById")),
            @Result(column = "{projectId = id, expertId = expertId, state = state}", property = "finish",
                    one= @One(select = "com.whu.mapper.ProjectAssignmentMapper.queryFinishByProjectIdAndExpertIdAndState"))
    })
    List<Project> queryProjectsByProjectIds(@Param(value = "projectIdList") List<Long> projectIdList,
                                            @Param(value = "expertId") Long expertId,
                                            @Param(value = "state") int state);

    /**
     * 查找表中所有的项目
     * @return 返回的项目列表
     */
    @Results({
            @Result(column = "prize_id", property = "prize",
                    one = @One(select = "com.whu.mapper.PrizeMapper.queryPrizeById")),
            @Result(column = "id", property = "fExpertName",
                    one = @One(select = "com.whu.mapper.ProjectAssignmentMapper.queryfExpertNameByProjectId")),
            @Result(column = "id", property = "lExpertName",
                    one = @One(select = "com.whu.mapper.ProjectAssignmentMapper.querylExpertNameByProjectId")),
            @Result(column = "id", property = "id")
            })
    @Select("SELECT * FROM t_project")
    List<Project> queryAllProjects();


    /**
     * 查找初评未被分配项目
     * @return 返回的初评未被分配的项目
     */
    @Results(@Result(column = "prize_id", property = "prize",
            one = @One(select = "com.whu.mapper.PrizeMapper.queryPrizeById")))
    @Select("SELECT * FROM t_project WHERE assessment_state = 1")
    List<Project> queryUnassignedProjectsF();

    /**
     * 查找会评未被分配项目
     * @return 返回的会评未被分配的项目
     */
    @Results(@Result(column = "prize_id", property = "prize",
            one = @One(select = "com.whu.mapper.PrizeMapper.queryPrizeById")))
    @Select("SELECT * FROM t_project WHERE assessment_state = 2")
    List<Project> queryUnassignedProjectsL();

    /**
     * 向项目表中插入数据
     * @param project
     * @return 事务执行结果状态
     */
    @Insert("INSERT INTO t_project VALUES(#{project.id}, #{prize_id}, " +
            "#{project.assessmentState}, #{project.prizeClass}, #{project.applicationTime}, #{project.name}," +
            "#{project.fGrade}, #{project.lGrade})")
    int insertProject(@Param(value = "project") Project project, @Param(value = "prize_id") Long prizeId);

}
