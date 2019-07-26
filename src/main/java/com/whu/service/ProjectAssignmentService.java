package com.whu.service;

import java.util.List;
import java.util.Set;

public interface ProjectAssignmentService
{
    int insertAssignments(int state, List<Long> projectIdList, Set<Long> expertIdSet);

    int insertAssignment(int state, List<Long> projectIdList, Long expertId);

    int deleteAssignments( List<Long> projectIdList, int state);

    int deleteAssignment(Long projectId, Long expertId, int state);
}
