package com.whu.service;

import com.whu.pojo.Project;

import java.util.List;
import java.util.Set;

public interface ProjectAssignmentService
{
    /**
     *
     * @param state
     * @param projectIdList
     * @param expertIdSet
     * @use AdminCtrl.autoAssign, 随机分配无候选专家类型的项目
     * @return
     */
    int insertAssignments(int state, List<Long> projectIdList, Set<Long> expertIdSet);

    /**
     *
     * @param state
     * @param projectIdList
     * @param expertIdSet
     * @use AdminCtrl.autoAssign, 随机分配有候选专家类型的项目
     * @return
     */
    int insertAssignmentsWithCandidate(int state, List<Long> projectIdList, Set<Long> expertIdSet,
                                       Set<Long> candidateIdSet);

    int insertAssignment(int state, List<Long> projectIdList, Long expertId);

    int deleteAssignments( List<Long> projectIdList, int state);

    int deleteAssignment(Long projectId, Long expertId, int state);

    /**
     * @param projects
     * @return
     * @use  AdminCtrl.get_all_projects:检测专家对应的项目是否评审完毕
     */
    List<Project> processExpertAssessmentState(List<Project> projects);

    List<String> queryFinshByProjectIdAndState(int state);

    List<String> queryCompletionStatus(int state);
}
