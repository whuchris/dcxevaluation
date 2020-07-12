package com.whu.ctrl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.mapper.*;
import com.whu.pojo.*;
import com.whu.service.*;
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

    @Autowired
    EnvirBenefit1Mapper envirBenefit1Mapper;

    @Autowired
    EnvirBenefit2Mapper envirBenefit2Mapper;

    @Autowired
    EnvirBenefit3Mapper envirBenefit3Mapper;

    @Autowired
    EnvirBenefit4Mapper envirBenefit4Mapper;

    @Autowired
    SocialBenefitMapper socialBenefitMapper;

    @Autowired
    EconoBenefitMapper econoBenefitMapper;

    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;

    @Autowired
    Type1Service type1Service;

    @Autowired
    Type2Service type2Service;

    @Autowired
    Type3Service type3Service;

    @Autowired
    Type4Service type4Service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        Admin admin;
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            String username = parameters.getString("username");
            String password = parameters.getString("password");

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
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            int state = parameters.getIntValue("state");
            Long expertId = parameters.getLong("expertId");
            JSONArray json = parameters.getJSONArray("projectIdList");

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
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            state = parameters.getIntValue("state");
            id = parameters.getLongValue("id");
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
        List<Project> projects = projectService.queryProjectsByState(state);
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
        Set<Long> candidateIdSet = new HashSet<>();
        Set<Long> wenTiExpertIdSet = new HashSet<>();
        Set<Long> yiYangExpertIdSet = new HashSet<>();

        for(Project project : projects)
        {
            int type = project.getPrize().getfType();
            Long projectId = project.getId();
            if(type == 1)
                chanChengProjectIdList.add(projectId);
            else if(type == 2)
                chengGengProjectIdList.add(projectId);
            else if(type == 3)
                juZhuProjectIdList.add(projectId);
            else if(type == 4)
                lvYouProjectIdList.add(projectId);
            else if(type == 5)
                shangBanProjectIdList.add(projectId);
            else if(type == 6)
                wenTiProjectIdList.add(projectId);
            else if(type == 7)
                yiYangProjectIdList.add(projectId);
        }

        for(Expert expert : experts)
        {
            List<Prize> prizeList = expert.getPrizeList();
            Long expertId = expert.getId();
            for(Prize prize : prizeList)
            {
                int type = prize.getfType();
                if(type == 1)
                    chanChengExpertIdSet.add(expertId);
                if(type == 2)
                    chengGengExpertIdSet.add(expertId);
                if(type == 3)
                    juZhuExpertIdSet.add(expertId);
                if(type == 4)
                    lvYouExpertIdSet.add(expertId);
                if(type == 5)
                    if(expert.getId() < 38L)
                        shangBanExpertIdSet.add(expertId);
                    else
                        candidateIdSet.add(expertId);
                if(type == 6)
                    wenTiExpertIdSet.add(expertId);
                if(type == 7)
                    yiYangExpertIdSet.add(expertId);
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

        int code5 = projectAssignmentService.
                insertAssignmentsWithCandidate(state, shangBanProjectIdList, shangBanExpertIdSet,candidateIdSet);
        if(code5 == 1)
        {
            result.put("shangBan", true);
        }
        else
        {
            result.put("shangBan", false);
        }

        int code6 = projectAssignmentService.
                insertAssignments(state, wenTiProjectIdList, wenTiExpertIdSet);
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
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            int state = parameters.getIntValue("state");
            JSONArray json = parameters.getJSONArray("projectIdList");

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
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            int state = parameters.getIntValue("state");
            Long projectId = parameters.getLongValue("projectId");
            Long expertId = parameters.getLongValue("expertId");

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

    /*
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
    }*/

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> select(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        List<Long> projectIdListToNext = new ArrayList<>();
        List<Long> projectIdListToRemain = new ArrayList<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            int state = parameters.getIntValue("state");


            JSONArray remainList = parameters.getJSONArray("projectIdListToRemain");
            for (int i = 0; i < remainList.size(); i++)
            {
                projectIdListToRemain.add(remainList.getLong(i));
            }

            JSONArray nextList = parameters.getJSONArray("projectIdListToNext");
            for (int i = 0; i < nextList.size(); i++)
            {
                projectIdListToNext.add(nextList.getLong(i));
            }

            if (state == 1)
            {
                Map<String, Object>  code= projectService.selectProjectsAtFirstAssessment(projectIdListToNext, projectIdListToRemain);
                if ((int)code.get("code") == 1)
                {
                    result.put("success", 1);
                    result.put("errMsg", null);
                }
                else if ((int)code.get("code") == -4)
                {
                    result.put("success", false);
                    result.put("errMsg", -4);
                    result.put("errProjectName", code.get("errProjectName"));
                }
                else
                {
                    result.put("success", false);
                    result.put("errMsg", -1);
                }
            }
            else if (state == 2)
            {
                int code = projectService.selectProjectsAtLastAssessment(projectIdListToNext,projectIdListToRemain);
                if (code == 1)
                {
                    result.put("success", 1);
                    result.put("errMsg", null);
                }
                else if (code == -4)
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
                result.put("success", false);
                result.put("errMsg", -2);
                return result;
            }

        }
        catch (IOException e)
        {
            result.put("success", false);
            result.put("errMsg", -2);
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
            result.put("success", false);
            result.put("errMsg", -3);
        }
        return result;
    }

    @RequestMapping(value = "/alter_grade_type1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alterGradeType1(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLongValue("projectId");
            float effect = parameters.getFloatValue("effect");
            float operationPerformance = parameters.getFloat("operationPerformance");
            float art = parameters.getFloatValue("art");
            float outdoorEnvir = parameters.getFloatValue("outdoorEnvir");
            float resourceUtilization = parameters.getFloatValue("resourceUtilization");
            float indoorEnvir = parameters.getFloatValue("indoorEnvir");
            float constructionManagement = parameters.getFloatValue("constructionManagement");
            float operationManagement = parameters.getFloatValue("operationManagement");
            float innovationEvaluation = parameters.getFloatValue("innovationEvaluation");
            float grade = parameters.getFloatValue("grade");

            EnvirBenefit1 envirBenefit1 = envirBenefit1Mapper.
                    queryScoreByProjectIdAndState(projectId, 3,-1L);
            envirBenefit1.setArt(art);
            envirBenefit1.setOutdoorEnvir(outdoorEnvir);
            envirBenefit1.setResourceUtilization(resourceUtilization);
            envirBenefit1.setIndoorEnvir(indoorEnvir);
            envirBenefit1.setConstructionManagement(constructionManagement);
            envirBenefit1.setInnovationEvaluation(innovationEvaluation);
            envirBenefit1.setOperationManagement(operationManagement);


            SocialBenefit socialBenefit = socialBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            socialBenefit.setEffect(effect);


            EconoBenefit econoBenefit = econoBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            econoBenefit.setOperationPerformance(operationPerformance);
            type1Service.alterType1Score(envirBenefit1,socialBenefit,econoBenefit,grade);
            result.put("success", true);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/alter_grade_type2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alterGradeType2(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLongValue("projectId");
            float effect = parameters.getFloat("effect");
            float operationPerformance = parameters.getFloat("operationPerformance");
            float art = parameters.getFloat("art");
            float landUsing = parameters.getFloat("landUsing");
            float informationManagement = parameters.getFloat("informationManagement");
            float envir = parameters.getFloat("envir");
            float greenTransportation = parameters.getFloat("greenTransportation");
            float grade = parameters.getFloat("grade");

            EnvirBenefit2 envirBenefit2 = envirBenefit2Mapper.
                    queryScoreByProjectIdAndState(projectId,3,-1L);
            envirBenefit2.setArt(art);
            envirBenefit2.setLandUsing(landUsing);
            envirBenefit2.setInformationManagement(informationManagement);
            envirBenefit2.setEnvir(envir);
            envirBenefit2.setGreenTransportation(greenTransportation);

            SocialBenefit socialBenefit = socialBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            socialBenefit.setEffect(effect);

            EconoBenefit econoBenefit = econoBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            econoBenefit.setOperationPerformance(operationPerformance);
            
            type2Service.alterType2Score(envirBenefit2,socialBenefit,econoBenefit,grade);
            result.put("success", true);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/alter_grade_type3", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alterGradeType3(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLongValue("projectId");
            float  effect = parameters.getFloat("effect");
            float  operationPerformance = parameters.getFloat("operationPerformance");
            float  art = parameters.getFloat("art");
            float  envirFriendliness = parameters.getFloat("envirFriendliness");
            float  projectFunction = parameters.getFloat("projectFunction");
            float  projectTechnology = parameters.getFloat("projectTechnology");
            float grade = parameters.getFloat("grade");

            EnvirBenefit3 envirBenefit3 = envirBenefit3Mapper.
                    queryScoreByProjectIdAndState(projectId, 3,-1L);
            envirBenefit3.setArt(art);
            envirBenefit3.setEnvirFriendliness(envirFriendliness);
            envirBenefit3.setProjectFunction(projectFunction);
            envirBenefit3.setProjectTechnology(projectTechnology);

            SocialBenefit socialBenefit = socialBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            socialBenefit.setEffect(effect);

            EconoBenefit econoBenefit = econoBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            econoBenefit.setOperationPerformance(operationPerformance);

            type3Service.alterType3Score(envirBenefit3,socialBenefit,econoBenefit,grade);
            result.put("success", true);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/alter_grade_type4", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alterGradeType4(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLongValue("projectId");
            float  effect = parameters.getFloat("effect");
            float  operationPerformance = parameters.getFloat("operationPerformance");
            float  culturalEnvir = parameters.getFloat("culturalEnvir");
            float  decorationMaterial = parameters.getFloat("decorationMaterial");
            float  decorationTechnology = parameters.getFloat("decorationTechnology");
            float  physicalEnvir = parameters.getFloat("physicalEnvir");
            float grade = parameters.getFloat("grade");

            EnvirBenefit4 envirBenefit4= envirBenefit4Mapper.
                    queryScoreByProjectIdAndState(projectId, 3,-1L);
            envirBenefit4.setCulturalEnvir(culturalEnvir);
            envirBenefit4.setDecorationMaterial(decorationMaterial);
            envirBenefit4.setDecorationTechnology(decorationTechnology);
            envirBenefit4.setPhysicalEnvir(physicalEnvir);

            SocialBenefit socialBenefit = socialBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            socialBenefit.setEffect(effect);

            EconoBenefit econoBenefit = econoBenefitMapper
                    .queryScoreByProjectIdAndState(projectId, 3,-1L);
            econoBenefit.setOperationPerformance(operationPerformance);

            type4Service.alterType4Score(envirBenefit4,socialBenefit,econoBenefit,grade);
            result.put("success", true);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/award", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> award(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLongValue("projectId");
            int prize = parameters.getIntValue("prize");
            Project project = projectService.queryProjectById(projectId);
            project.setPrizeClass(prize);
            projectService.updateProject(project);
            result.put("success", true);
            result.put("errMsg", null);
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg", -1);
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

    @RequestMapping(value = "/get_project_assessment_state/{state}/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProjectAssessmentState(@PathVariable(value = "state") int state,
                                                         @PathVariable(value = "projectId") Long projectId)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            List<ProjectAssignment> projectAssignments = projectAssignmentMapper.
                    queryAssignmentsByProjectIdAndState(projectId,state);
            List<String> expertNameList = new ArrayList<>();
            for(ProjectAssignment projectAssignment: projectAssignments)
            {
                if(projectAssignment.getFinish() == 0)
                {
                    Expert expert = expertService.queryExpertById(projectAssignment.getExpertId());
                    expertNameList.add(expert.getName());
                }
            }
            result.put("expertNameList", expertNameList);
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
    @RequestMapping(value = "/get_project_avg_grade/{type}/{projectId}/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryAvgScoreByTypeAndProjectIdAndState(@PathVariable("type") int type,
                                                                       @PathVariable("projectId") Long projectId,
                                                                       @PathVariable("state") int state)
    {
        Map<String, Object> result = new HashMap<>();
        switch (type)
        {
            case 1:
                try
                {
                    Map<String, Float> avgScore1 = type1Service.queryScoresByProjectIdAndState(projectId, state);
                    Project project1 = projectService.queryProjectById(projectId);
                    if (avgScore1 != null)
                    {

                        result.put("success", 1);
                        result.put("errMsg", null);
                        result.put("avgOperationPerformance", avgScore1.get("avgOperationPerformance"));
                        result.put("avgEffect", avgScore1.get("avgEffect"));
                        result.put("avgArt", avgScore1.get("avgArt"));
                        result.put("avgIndoorEnvir", avgScore1.get("avgIndoorEnvir"));
                        result.put("avgConstructionManagement", avgScore1.get("avgConstructionManagement"));
                        result.put("avgInnovationEvaluation", avgScore1.get("avgInnovationEvaluation"));
                        result.put("avgOperationManagement", avgScore1.get("avgOperationManagement"));
                        result.put("avgOutdoorEnvir", avgScore1.get("avgOutdoorEnvir"));
                        result.put("avgResourceUtilization", avgScore1.get("avgResourceUtilization"));
                        if (state == 1)
                            result.put("grade", project1.getfGrade());
                        else if(state == 2)
                            result.put("grade", project1.getlGrade());
                        else
                            result.put("grade", project1.getFinalGrade());
                    }
                    else
                    {
                        result.put("success", 0);
                        result.put("errMsg", "-1");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", 0);
                    result.put("errMsg", "1");
                }
                break;
            case 2:
                try
                {
                    Map<String, Float> avgScore2 = type2Service.queryScoresByProjectIdAndState(projectId,state);
                    Project project2 = projectService.queryProjectById(projectId);
                    if(avgScore2!= null)
                    {

                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("avgOperationPerformance", avgScore2.get("avgOperationPerformance"));
                        result.put("avgEffect", avgScore2.get("avgEffect"));
                        result.put("avgArt", avgScore2.get("avgArt"));
                        result.put("avgInformationManagement", avgScore2.get("avgInformationManagement"));
                        result.put("avgGreenTransportation", avgScore2.get("avgGreenTransportation"));
                        result.put("avgEnvir", avgScore2.get("avgEnvir"));
                        result.put("avgLandUsing", avgScore2.get("avgLandUsing"));
                        if(state == 1)
                            result.put("grade", project2.getfGrade());
                        else if(state == 2)
                            result.put("grade", project2.getlGrade());
                        else
                            result.put("grade", project2.getFinalGrade());
                    }
                    else
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", 0);
                    result.put("errMsg", "1");
                }
                break;
            case 3:
                try
                {
                    Map<String, Float> avgScore3 = type3Service.queryScoresByProjectIdAndState(projectId,state);
                    Project project3 = projectService.queryProjectById(projectId);
                    if(avgScore3 != null)
                    {

                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("avgOperationPerformance", avgScore3.get("avgOperationPerformance"));
                        result.put("avgEffect", avgScore3.get("avgEffect"));
                        result.put("avgArt", avgScore3.get("avgArt"));
                        result.put("avgEnvirFriendliness", avgScore3.get("avgEnvirFriendliness"));
                        result.put("avgProjectFunction", avgScore3.get("avgProjectFunction"));
                        result.put("avgProjectTechnology", avgScore3.get("avgProjectTechnology"));
                        if(state == 1)
                            result.put("grade", project3.getfGrade());
                        else if(state == 2)
                            result.put("grade", project3.getlGrade());
                        else
                            result.put("grade", project3.getFinalGrade());
                    }
                    else
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", 0);
                    result.put("errMsg", "1");
                }
                break;
            case 4:
                try
                {
                    Map<String, Float> avgScore4 = type4Service.queryScoresByProjectIdAndState(projectId,state);
                    Project project4 = projectService.queryProjectById(projectId);
                    if(avgScore4 != null)
                    {

                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("avgOperationPerformance", avgScore4.get("avgOperationPerformance"));
                        result.put("avgEffect", avgScore4.get("avgEffect"));
                        result.put("avgCulturalEnvir", avgScore4.get("avgCulturalEnvir"));
                        result.put("avgPhysicalEnvir", avgScore4.get("avgPhysicalEnvir"));
                        result.put("avgDecorationMaterial", avgScore4.get("avgDecorationMaterial"));
                        result.put("avgDecorationTechnology", avgScore4.get("avgDecorationTechnology"));
                        if(state == 1)
                            result.put("grade", project4.getfGrade());
                        else if(state == 2)
                            result.put("grade", project4.getlGrade());
                        else
                            result.put("grade", project4.getFinalGrade());
                    }
                    else
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    result.put("success", 0);
                    result.put("errMsg", "1");
                }

                break;
        }
        return result;
    }

    @RequestMapping(value = "/get_not_beginning_experts/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getNotBeginningExperts(@PathVariable("state") int state)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            List<String> experts = projectAssignmentService.queryFinshByProjectIdAndState(state);
            result.put("success", 1);
            result.put("errMsg", null);
            result.put("experts", experts);
        }
        catch(Exception e)
        {
            result.put("success", false);
            result.put("errMsg", -1);
        }
        return result;
    }

    @RequestMapping(value = "/get_completion_status/{state}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCompletionStatus(@PathVariable("state") int state)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            List<String> status = projectAssignmentService.queryCompletionStatus(state);
            result.put("success", 1);
            result.put("errMsg", null);
            result.put("status", status);
        }
        catch(Exception e)
        {
            result.put("success", false);
            result.put("errMsg", -1);
        }
        return result;
    }
}
