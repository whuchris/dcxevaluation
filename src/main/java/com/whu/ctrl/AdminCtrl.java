package com.whu.ctrl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.pojo.Admin;
import com.whu.pojo.Expert;
import com.whu.pojo.Prize;
import com.whu.pojo.Project;
import com.whu.service.AdminService;
import com.whu.service.ExpertService;
import com.whu.service.ProjectAssignmentService;
import com.whu.service.ProjectService;
import com.whu.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminCtrl
{
    @Autowired
    AdminService adminService;

    @Autowired
    ExpertService expertService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectAssignmentService projectAssignmentService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        Admin admin;
        try
        {
            JSONObject paramaters = JsonUtil.getRequestJsonObject(request);
            String username = paramaters.getString("username");
            String password = paramaters.getString("password");

            admin = adminService.queryAdminByUsername(username);

            if(admin == null) //用户不存在
            {
                result.put("success", false);
                result.put("errMsg", 1);
            }
            else if(!admin.getPassword().equals(password)) //密码错误
            {
                result.put("success", false);
                result.put("errMsg", 0);
            }
            else  //认证成功
            {
                result.put("success", 1);
                result.put("errMsg", "");
                result.put("name", admin.getName());
                result.put("id", admin.getId());
                HttpSession session= (HttpSession)request.getSession();
                session.setAttribute("currentAdmin", admin);
            }
        }
        catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", "Unknow error!");
        }

        return result;
    }


    @RequestMapping(value = "/get_projects_unassigned/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUnassignedProjects(@PathVariable(value = "state") int state)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            if(state == 1)
            {
                List<Project> projects = projectService.queryUnassignedProjectsF();
                result.put("success",1);
                result.put("errMsg", null);
                result.put("projects", projects);
            }
            else if(state == 2)
            {
                List<Project> projects = projectService.queryUnassignedProjectsL();
                result.put("success",1);
                result.put("errMsg", null);
                result.put("projects", projects);
            }
            else
            {
                result.put("success",false);
                result.put("errMsg", -2);
            }
        }
        catch(Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
        }
        return result;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assign(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            List<Long> projectIdList = new ArrayList<>();
            JSONObject paramaters = JsonUtil.getRequestJsonObject(request);
            int state = paramaters.getIntValue("state");
            Long expertId = paramaters.getLong("expertId");
            JSONArray json = paramaters.getJSONArray("projectIdList");

            for(int i = 0; i < json.size(); i++)
                projectIdList.add(json.getLong(i));

            int code = projectAssignmentService.insertAssignment(state,projectIdList,expertId);
            if(code == 1) //分配成功
            {
                result.put("success",1);
                result.put("errMsg", null);
            }
            else if(code == -2)  //分配失败，该项目已经分配给该专家了
            {
                result.put("success",false);
                result.put("errMsg", -2);
            }
            else  //未知错误
            {
                result.put("success", false);
                result.put("errMsg", -1);
            }
        }
        catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", -1);

        }
        return result;
    }

    @RequestMapping(value = "/auto_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> autoAssign(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        int state = 0;
        Long id = 0L;
        try
        {
            JSONObject paramaters = JsonUtil.getRequestJsonObject(request);
            state = paramaters.getIntValue("state");
            id = paramaters.getLongValue("id");
        }
        catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", -1);
            return result;
        }
        Admin admin = adminService.queryAdminById(id);
        if(state != 1 && state != 2)
        {
            result.put("success", false);
            result.put("errMsg", -3);
            return result;
        }
        if((admin.getAutoAssign1() == 1 && state == 1) ||
                admin.getAutoAssign2() == 1 && state == 2)
        {
            result.put("success", false);
            result.put("errMsg", -2);
            return result;
        }
        List<Project> projects = projectService.queryAllProjects();
        List<Expert> experts = expertService.queryAllExperts();

        List<Long> chanChengProjectIdList = new ArrayList<>();
        List<Long> chengGengProjectIdList = new ArrayList<>();
        List<Long> juZhuProjectIdList = new ArrayList<>();
        List<Long> lvYouProjectIdList = new ArrayList<>();
        List<Long> shangBanProjectIdList = new ArrayList<>();
        List<Long> wenTiProjectIdList = new ArrayList<>();
        List<Long> yiYangProjectIdList = new ArrayList<>();

        Set<Long> chanChengExpertIdSet = new HashSet<>();
        Set<Long> chengGengExpertIdSet = new HashSet<>();
        Set<Long> juZhuExpertIdSet = new HashSet<>();
        Set<Long> lvYouExpertIdSet = new HashSet<>();
        Set<Long> shangBanExpertIdSet = new HashSet<>();
        Set<Long> wenTiExpertIdSet = new HashSet<>();
        Set<Long> yiYangExpertIdSet = new HashSet<>();

        for(Project project : projects)
        {
            int type = project.getPrize().getfType();
            if(type == 1)
                chanChengProjectIdList.add(project.getId());
            else if(type == 2)
                chengGengProjectIdList.add(project.getId());
            else if(type == 3)
                juZhuProjectIdList.add(project.getId());
            else if(type == 4)
                lvYouProjectIdList.add(project.getId());
            else if(type == 5)
                shangBanProjectIdList.add(project.getId());
            else if(type == 6)
                wenTiProjectIdList.add(project.getId());
            else if(type == 7)
                yiYangProjectIdList.add(project.getId());
        }

        for(Expert expert : experts)
        {
            for(Prize prize : expert.getPrizeList())
            {
                int type = prize.getfType();
                if(type == 1)
                    chanChengExpertIdSet.add(expert.getId());
                if(type == 2)
                    chengGengExpertIdSet.add(expert.getId());
                if(type == 3)
                    juZhuExpertIdSet.add(expert.getId());
                if(type == 4)
                    lvYouExpertIdSet.add(expert.getId());
                if(type == 5)
                    shangBanExpertIdSet.add(expert.getId());
                if(type == 6)
                    wenTiExpertIdSet.add(expert.getId());
                if(type == 7)
                    yiYangExpertIdSet.add(expert.getId());
            }
        }

        int code1 = projectAssignmentService.insertAssignments(state, chanChengProjectIdList, chanChengExpertIdSet);
        if(code1 == 1)
        {
            result.put("chancheng", true);
        }
        else
        {
            result.put("chancheng", false);
        }
        int code2 = projectAssignmentService.insertAssignments(state, chengGengProjectIdList, chengGengExpertIdSet);
        if(code2 == 1)
        {
            result.put("chengGeng", true);
        }
        else
        {
            result.put("chengGeng", false);
        }

        int code3 = projectAssignmentService.insertAssignments(state, juZhuProjectIdList, juZhuExpertIdSet);
        if(code3 == 1)
        {
            result.put("juZhu", true);
        }
        else
        {
            result.put("juZhu", false);
        }

        int code4 = projectAssignmentService.insertAssignments(state, lvYouProjectIdList, lvYouExpertIdSet);
        if(code1 == 1)
        {
            result.put("lvYou", true);
        }
        else
        {
            result.put("lvYou", false);
        }

        int code5 = projectAssignmentService.insertAssignments(state, shangBanProjectIdList, shangBanExpertIdSet);
        if(code5 == 1)
        {
            result.put("shangBan", true);
        }
        else
        {
            result.put("shangBan", false);
        }

        int code6 = projectAssignmentService.insertAssignments(state, wenTiProjectIdList, wenTiExpertIdSet);
        if(code6 == 1)
        {
            result.put("wenTi", true);
        }
        else
        {
            result.put("wenTi", false);
        }

        int code7 = projectAssignmentService.insertAssignments(state, yiYangProjectIdList, yiYangExpertIdSet);
        if(code7 == 1)
        {
            result.put("yiYang", true);
        }
        else
        {
            result.put("yiYang", false);
        }

        if(code1 == 1 && code2 == 1 && code3 == 1 && code4 == 1 && code5 == 1 && code6 == 1 && code7 == 1)
        {
            result.put("success", true);
            result.put("errMsg", null);
        }
        else
        {
            result.put("success", false);
            result.put("errMsg", -1);
        }
        /*result.put("chanChengProjectIdList",chanChengProjectIdList);
        result.put("chengGengProjectIdList",chengGengProjectIdList);
        result.put("shangBanProjectIdLists",shangBanProjectIdList);
        result.put("juZhuProjectIdList",juZhuProjectIdList);
        result.put("chanChengExpertIdList",chanChengExpertIdSet);
        result.put("shangBanExpertIdList",shangBanExpertIdSet);
        result.put("juZhuExpertIdList",juZhuExpertIdSet);
        result.put("chengGengExpertIdList",chengGengExpertIdSet);*/
        return result;
    }

    /*
    @RequestMapping(value = "/withdraw_assignments", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> withdrawAssign(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            List<Long> projectIdList = new ArrayList<>();
            JSONObject paramaters = JsonUtil.getRequestJsonObject(request);
            int state = paramaters.getIntValue("state");
            JSONArray json = paramaters.getJSONArray("projectIdList");

            for(int i = 0; i < json.size(); i++)
                projectIdList.add(json.getLong(i));

            int code = projectAssignmentService.deleteAssignments(projectIdList, state);
            if(code == 1) //撤回成功
            {
                result.put("success",1);
                result.put("errMsg", null);
            }
            else if(code == -2)  //分配失败，存在已评分的项目
            {
                result.put("success",false);
                result.put("errMsg", -2);
            }
            else if(code == -3) //state参数错误
            {
                result.put("success",false);
                result.put("errMsg", -3);
            }
            else  //未知错误
            {
                result.put("success", false);
                result.put("errMsg", -1);
            }
        }
        catch (Exception e) //未知错误
        {
            result.put("success", false);
            result.put("errMsg", -1);

        }
        return result;
    }*/

    @RequestMapping(value = "/withdraw_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> withdrawAssign(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            List<Long> projectIdList = new ArrayList<>();
            JSONObject paramaters = JsonUtil.getRequestJsonObject(request);
            int state = paramaters.getIntValue("state");
            Long projectId = paramaters.getLongValue("projectId");
            Long expertId = paramaters.getLongValue("expertId");

            int code = projectAssignmentService.deleteAssignment(projectId,expertId,state);
            if(code == 1) //撤回成功
            {
                result.put("success",1);
                result.put("errMsg", null);
            }
            else if(code == -2)  //撤回失败，项目已评分
            {
                result.put("success",false);
                result.put("errMsg", -2);
            }
            else if(code == -3) //state参数错误
            {
                result.put("success",false);
                result.put("errMsg", -3);
            }
            else  //未知错误
            {
                result.put("success", false);
                result.put("errMsg", -1);
            }
        }
        catch (Exception e) //未知错误
        {
            e.printStackTrace();
            result.put("success", false);
            result.put("errMsg", -1);

        }
        return result;
    }

    /*
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> select(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            List<Long> projectIdListToPromotioin = new ArrayList<>();
            List<Long> projectIdListToRemain = new ArrayList<>();
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);

            int state = parameters.getIntValue("state");

            JSONArray promotionList = parameters.getJSONArray("projectIdListToPromotion");
            for(int i = 0; i < promotionList.size(); i++)
            {
                projectIdListToPromotioin.add(promotionList.getLong(i));
            }

            JSONArray remainList = parameters.getJSONArray("projectIdListToRemain");
            for(int i = 0; i < remainList.size(); i++)
            {
                projectIdListToRemain.add(remainList.getLong(i));
            }

            if(state == 1)
            {
                JSONArray nextList = parameters.getJSONArray("projectIdListToNext");
                List<Long> projectIdListToNext = new ArrayList<>();

                for(int i = 0; i < nextList.size(); i++)
                {
                    projectIdListToNext.add(nextList.getLong(i));
                }

                int code = projectService.selectProjectsAtFirstAssessment(projectIdListToNext, projectIdListToRemain);
                if(code == 1)
                {
                    result.put("success",1);
                    result.put("errMsg", null);
                }
                else if(code == -4)
                {
                    result.put("success", false);
                    result.put("errMsg", -4);
                }
                else
                {
                    result.put("success", false);
                    result.put("errMsg", -1);
                }
            }
            else if(state == 2)
            {
                for(int i = 0; i < promotionList.size(); i++)
                {
                    projectIdListToPromotioin.add(promotionList.getLong(i));
                }
                int code = projectService.selectProjectsAtLastAssessment(projectIdListToPromotioin,
                                                                         projectIdListToRemain);
                if(code == 1)
                {
                    result.put("success",1);
                    result.put("errMsg", null);
                }
                else if(code == -4)
                {
                    result.put("success", false);
                    result.put("errMsg", -4);
                }
                else
                {
                    result.put("success", false);
                    result.put("errMsg", -1);
                }
            }
            else
            {
                result.put("success",false);
                result.put("errMsg", -2);
                return result;
            }

        }
        catch (IOException e)
        {
            result.put("success",false);
            result.put("errMsg", -2);
        }
        catch (RuntimeException e)
        {
            result.put("success",false);
            result.put("errMsg", -3);
        }
        return result;
    }
    */

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> select(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        List<Long> projectIdListToPromotioin = new ArrayList<>();
        List<Long> projectIdListToRemain = new ArrayList<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            int state = parameters.getIntValue("state");


            JSONArray remainList = parameters.getJSONArray("projectIdListToRemain");
            for(int i = 0; i < remainList.size(); i++)
            {
                projectIdListToRemain.add(remainList.getLong(i));
            }

            if(state == 1)
            {
                JSONArray nextList = parameters.getJSONArray("projectIdListToNext");
                List<Long> projectIdListToNext = new ArrayList<>();

                for(int i = 0; i < nextList.size(); i++)
                {
                    projectIdListToNext.add(nextList.getLong(i));
                }

                int code = projectService.selectProjectsAtFirstAssessment(projectIdListToNext, projectIdListToRemain);
                if(code == 1)
                {
                    result.put("success",1);
                    result.put("errMsg", null);
                }
                else if(code == -4)
                {
                    result.put("success", false);
                    result.put("errMsg", -4);
                }
                else
                {
                    result.put("success", false);
                    result.put("errMsg", -1);
                }
            }
            else if(state == 2)
            {
                JSONArray promotionList = parameters.getJSONArray("projectIdListToPromotion");
                for(int i = 0; i < promotionList.size(); i++)
                {
                    projectIdListToPromotioin.add(promotionList.getLong(i));
                }

                int code = projectService.selectProjectsAtLastAssessment(projectIdListToPromotioin,
                        projectIdListToRemain);
                if(code == 1)
                {
                    result.put("success",1);
                    result.put("errMsg", null);
                }
                else if(code == -4)
                {
                    result.put("success", false);
                    result.put("errMsg", -4);
                }
                else
                {
                    result.put("success", false);
                    result.put("errMsg", -1);
                }
            }
            else
            {
                result.put("success",false);
                result.put("errMsg", -2);
                return result;
            }

        }
        catch (IOException e)
        {
            result.put("success",false);
            result.put("errMsg", -2);
        }
        catch (RuntimeException e)
        {
            result.put("success",false);
            result.put("errMsg", -3);
        }
        return result;
    }

    @RequestMapping(value = "/get_all_experts", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllExperts(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        List<Expert> experts = new ArrayList<>();
        try
        {
            experts = expertService.queryAllExperts();
            result.put("experts", experts);
            result.put("success",1);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
        }
        return result;
    }

    @RequestMapping(value = "/get_all_projects", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllProjects(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        List<Project> projects = new ArrayList<>();
        try
        {
            projects = projectService.queryAllProjects();
            int autoAssign1 = adminService.getFirstState();
            int autoAssign2 = adminService.getLastState();
            result.put("autoAssign1", autoAssign1);
            result.put("autoAssign2", autoAssign2);
            result.put("projects", projects);
            result.put("success",1);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("success",false);
            result.put("errMsg", -1);
        }
        return result;
    }


}
