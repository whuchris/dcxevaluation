package com.whu.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.whu.mapper.ProjectAssignmentMapper;
import com.whu.pojo.*;
import com.whu.service.*;
import com.whu.utils.HttpServletRequestUtil;
import com.whu.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/expert")
public class ExpertCtrl
{
    @Autowired
    ExpertService expertService;

    @Autowired
    ProjectService projectService;

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
        Expert expert;
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            String username = parameters.getString("username");
            String password = parameters.getString("password");

            expert = expertService.queryExpertByUsername(username);

            if(expert == null) //用户不存在
            {
                result.put("success", false);
                result.put("errMsg", 1);
            }
            else if(!expert.getPassword().equals(password)) //密码错误
            {
                result.put("success", false);
                result.put("errMsg", 0);
            }
            else  //认证成功
            {
                result.put("success", 1);
                result.put("errMsg", "");
                result.put("name", expert.getName());
                result.put("id", expert.getId());
                HttpSession session= (HttpSession)request.getSession();
                session.setAttribute("currentExpert", expert);
            }
        }
        catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", "Unknow error!");
        }

        return result;
    }

    @RequestMapping(value = "/alter_password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alterPassword(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        Expert expert;
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long id = parameters.getLong("id");
            String newPassword =  parameters.getString("newPassword");
            String password = parameters.getString("password");

            expert = expertService.queryExpertById(id);

            if(expert == null) //用户不存在
            {
                result.put("success", false);
                result.put("errMsg", 1);
            }
            else if(!expert.getPassword().equals(password)) //旧密码错误
            {
                result.put("success", false);
                result.put("errMsg", 0);
            }
            else  //修改成功
            {
                expert.setPassword(newPassword);
                expertService.updateExpert(expert);
                result.put("success", 1);
                result.put("errMsg", "");

                HttpSession session= (HttpSession)request.getSession();
                session.setAttribute("currentExpert", expert);
            }
        }
        catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", -1);
        }

        return result;
    }

    @RequestMapping(value = "/my_projects/{id}/{state}")
    @ResponseBody
    public Map<String, Object> queryProjetsByExpertId(@PathVariable("id") Long id, @PathVariable("state") int state)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            List<Project> projects = projectService.queryProjectsByExpertIdAndState(id, state);
            result.put("success", 1);
            result.put("errMsg", "");
            result.put("projects", projects);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("success", false);
            result.put("errMsg", "-1");
        }
        return result;
    }

    @RequestMapping(value = "/my_types/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPrizesByExpertId(@PathVariable("id") Long id)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            Expert expert = expertService.queryExpertById(id);
            result.put("success", 1);
            result.put("errMsg", "");
            result.put("prizeList", expert.getPrizeList());
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
            result.put("success", false);
            result.put("errMsg", "-2");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("success", false);
            result.put("errMsg", "-1");
        }
        return result;
    }

    @RequestMapping(value = "/assess_type1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessType1(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLong("projectId");
            Long expertId = parameters.getLong("expertId");
            int state = parameters.getIntValue("state");
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

            EnvirBenefit1 envirBenefit1 = new EnvirBenefit1();
            envirBenefit1.setExpertId(expertId);
            envirBenefit1.setProjectId(projectId);
            envirBenefit1.setArt(art);
            envirBenefit1.setOutdoorEnvir(outdoorEnvir);
            envirBenefit1.setResourceUtilization(resourceUtilization);
            envirBenefit1.setIndoorEnvir(indoorEnvir);
            envirBenefit1.setConstructionManagement(constructionManagement);
            envirBenefit1.setInnovationEvaluation(innovationEvaluation);
            envirBenefit1.setOperationManagement(operationManagement);
            envirBenefit1.setState(state);

            SocialBenefit socialBenefit = new SocialBenefit();
            socialBenefit.setExpertId(expertId);
            socialBenefit.setProjectId(projectId);
            socialBenefit.setEffect(effect);
            socialBenefit.setState(state);

            EconoBenefit econoBenefit = new EconoBenefit();
            econoBenefit.setExpertId(expertId);
            econoBenefit.setProjectId(projectId);
            econoBenefit.setOperationPerformance(operationPerformance);
            econoBenefit.setState(state);

            int code = type1Service.insertType1Score(envirBenefit1,socialBenefit,econoBenefit, grade);
            if(code == 1)
            {
                result.put("success",1);
                result.put("errMsg",null);
            }
            else if(code == -2)  // no right to assess
            {
                result.put("success",false);
                result.put("errMsg",-2);
            }
            else if(code == -1) //project has been assessed already
            {
                result.put("success",false);
                result.put("errMsg",-1);
            }
            else
            {
                result.put("success",false);
                result.put("errMsg",1);
            }
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg",1);
        }
        return result;
    }

    @RequestMapping(value = "/assess_type2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessType2(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLong("projectId");
            Long expertId = parameters.getLong( "expertId");
            int state = parameters.getIntValue("state");
            float effect = parameters.getFloat("effect");
            float operationPerformance = parameters.getFloat("operationPerformance");
            float art = parameters.getFloat("art");
            float landUsing = parameters.getFloat("landUsing");
            float informationManagement = parameters.getFloat("informationManagement");
            float envir = parameters.getFloat("envir");
            float greenTransportation = parameters.getFloat("greenTransportation");
            float grade = parameters.getFloat("grade");

            EnvirBenefit2 envirBenefit2 = new EnvirBenefit2();
            envirBenefit2.setExpertId(expertId);
            envirBenefit2.setProjectId(projectId);
            envirBenefit2.setArt(art);
            envirBenefit2.setLandUsing(landUsing);
            envirBenefit2.setInformationManagement(informationManagement);
            envirBenefit2.setEnvir(envir);
            envirBenefit2.setGreenTransportation(greenTransportation);
            envirBenefit2.setState(state);

            SocialBenefit socialBenefit = new SocialBenefit();
            socialBenefit.setExpertId(expertId);
            socialBenefit.setProjectId(projectId);
            socialBenefit.setEffect(effect);
            socialBenefit.setState(state);

            EconoBenefit econoBenefit = new EconoBenefit();
            econoBenefit.setExpertId(expertId);
            econoBenefit.setProjectId(projectId);
            econoBenefit.setOperationPerformance(operationPerformance);
            econoBenefit.setState(state);

            int code = type2Service.insertType2Score(envirBenefit2,socialBenefit,econoBenefit, grade);
            if(code == 1)
            {
                result.put("success",1);
                result.put("errMsg",null);
            }
            else if(code == -2)  // no right to assess
            {
                result.put("success",false);
                result.put("errMsg",-2);
            }
            else if(code == -1) //project has been assessed already
            {
                result.put("success",false);
                result.put("errMsg",-1);
            }
            else
            {
                result.put("success",false);
                result.put("errMsg",1);
            }
            return result;
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg",1);
            return result;
        }
    }

    @RequestMapping(value = "/assess_type3", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessType3(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLong("projectId");
            Long expertId = parameters.getLong("expertId");
            int state = parameters.getIntValue("state");
            float  effect = parameters.getFloat("effect");
            float  operationPerformance = parameters.getFloat("operationPerformance");
            float  art = parameters.getFloat("art");
            float  envirFriendliness = parameters.getFloat("envirFriendliness");
            float  projectFunction = parameters.getFloat("projectFunction");
            float  projectTechnology = parameters.getFloat("projectTechnology");
            float grade = parameters.getFloat("grade");


            EnvirBenefit3 envirBenefit3 = new EnvirBenefit3();
            envirBenefit3.setExpertId(expertId);
            envirBenefit3.setProjectId(projectId);
            envirBenefit3.setArt(art);
            envirBenefit3.setEnvirFriendliness(envirFriendliness);
            envirBenefit3.setProjectFunction(projectFunction);
            envirBenefit3.setProjectTechnology(projectTechnology);
            envirBenefit3.setState(state);

            SocialBenefit socialBenefit = new SocialBenefit();
            socialBenefit.setExpertId(expertId);
            socialBenefit.setProjectId(projectId);
            socialBenefit.setEffect(effect);
            socialBenefit.setState(state);

            EconoBenefit econoBenefit = new EconoBenefit();
            econoBenefit.setExpertId(expertId);
            econoBenefit.setProjectId(projectId);
            econoBenefit.setOperationPerformance(operationPerformance);
            econoBenefit.setState(state);

            int code = type3Service.insertType3Score(envirBenefit3,socialBenefit,econoBenefit, grade);
            if(code == 1)
            {
                result.put("success",1);
                result.put("errMsg",null);
            }
            else if(code == -2)  // no right to assess
            {
                result.put("success",false);
                result.put("errMsg",-2);
            }
            else if(code == -1) //project has been assessed already
            {
                result.put("success",false);
                result.put("errMsg",-1);
            }
            else
            {
                result.put("success",false);
                result.put("errMsg",1);
            }
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg",1);

        }
        return result;
    }

    @RequestMapping(value = "/assess_type4", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessType4(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            JSONObject parameters = JsonUtil.getRequestJsonObject(request);
            Long projectId = parameters.getLong("projectId");
            Long expertId = parameters.getLong("expertId");
            int state = parameters.getIntValue("state");
            float  effect = parameters.getFloat("effect");
            float  operationPerformance = parameters.getFloat("operationPerformance");
            float  culturalEnvir = parameters.getFloat("culturalEnvir");
            float  decorationMaterial = parameters.getFloat("decorationMaterial");
            float  decorationTechnology = parameters.getFloat("decorationTechnology");
            float  physicalEnvir = parameters.getFloat("physicalEnvir");
            float grade = parameters.getFloat("grade");

            EnvirBenefit4 envirBenefit4= new EnvirBenefit4();
            envirBenefit4.setExpertId(expertId);
            envirBenefit4.setProjectId(projectId);
            envirBenefit4.setCulturalEnvir(culturalEnvir);
            envirBenefit4.setDecorationMaterial(decorationMaterial);
            envirBenefit4.setDecorationTechnology(decorationTechnology);
            envirBenefit4.setPhysicalEnvir(physicalEnvir);
            envirBenefit4.setState(state);

            SocialBenefit socialBenefit = new SocialBenefit();
            socialBenefit.setExpertId(expertId);
            socialBenefit.setProjectId(projectId);
            socialBenefit.setEffect(effect);
            socialBenefit.setState(state);

            EconoBenefit econoBenefit = new EconoBenefit();
            econoBenefit.setExpertId(expertId);
            econoBenefit.setProjectId(projectId);
            econoBenefit.setOperationPerformance(operationPerformance);
            econoBenefit.setState(state);

            int code = type4Service.insertType4Score(envirBenefit4,socialBenefit,econoBenefit, grade);
            if(code == 1)
            {
                result.put("success",1);
                result.put("errMsg",null);
            }
            else if(code == -2)  // no right to assess
            {
                result.put("success",false);
                result.put("errMsg",-2);
            }
            else if(code == -1) //project has been assessed already
            {
                result.put("success",false);
                result.put("errMsg",-1);
            }
            else
            {
                result.put("success",false);
                result.put("errMsg",1);
            }
            return result;
        }
        catch (Exception e)
        {
            result.put("success",false);
            result.put("errMsg",1);
            return result;
        }
    }

    @RequestMapping(value = "/get_project_grade/{type}/{projectId}/{state}/{expertId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryScoreByTypeAndProjectIdAndState(@PathVariable("type") int type,
                                                                  @PathVariable("projectId") Long projectId,
                                                                  @PathVariable("state") int state,
                                                                  @PathVariable("expertId") Long expertId)
    {
        Map<String, Object> result = new HashMap<>();
        Map<String, Benefit> score;
        ProjectAssignment projectAssignment =
                projectAssignmentMapper.queryAssignmentByProjectIdAndExpertIdAndState(projectId,expertId,state);
        switch (type)
        {
            case 1:
                score = type1Service.queryScoreByProjectIdAndState(projectId,state,expertId);

                if(score != null)
                {
                    EnvirBenefit1 envirBenefit1 = (EnvirBenefit1)score.get("envirBenefit1");
                    if(envirBenefit1 == null)
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                    else
                    {
                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("envirBenefit", envirBenefit1);
                        result.put("socialBenefit", (SocialBenefit)score.get("socialBenefit"));
                        result.put("econoBenefit", (EconoBenefit)score.get("econoBenefit"));
                    }
                }
                else
                {
                    result.put("success",0);
                    result.put("errMsg", "1");
                }
                break;
            case 2:
                score = type2Service.queryScoreByProjectIdAndState(projectId,state,expertId);
                Project project2 = projectService.queryProjectById(projectId);
                if(score != null)
                {
                    EnvirBenefit2 envirBenefit2 = (EnvirBenefit2)score.get("envirBenefit2");
                    if(envirBenefit2 == null)
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                    else
                    {
                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("envirBenefit", envirBenefit2);
                        result.put("socialBenefit", (SocialBenefit)score.get("socialBenefit"));
                        result.put("econoBenefit", (EconoBenefit)score.get("econoBenefit"));
                    }
                }
                else
                {
                    result.put("success",0);
                    result.put("errMsg", "1");
                }
                break;
            case 3:
                score = type3Service.queryScoreByProjectIdAndState(projectId,state,expertId);
                Project project3 = projectService.queryProjectById(projectId);
                if(score != null)
                {
                    EnvirBenefit3 envirBenefit3 = (EnvirBenefit3)score.get("envirBenefit3");
                    if(envirBenefit3 == null)
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                    else
                    {
                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("envirBenefit", envirBenefit3);
                        result.put("socialBenefit", (SocialBenefit)score.get("socialBenefit"));
                        result.put("econoBenefit", (EconoBenefit)score.get("econoBenefit"));
                    }
                }
                else
                {
                    result.put("success",0);
                    result.put("errMsg", "1");
                }
                break;
            case 4:
                score = type4Service.queryScoreByProjectIdAndState(projectId,state,expertId);
                Project project4 = projectService.queryProjectById(projectId);
                if(score != null)
                {
                    EnvirBenefit4 envirBenefit4 = (EnvirBenefit4)score.get("envirBenefit4");
                    if(envirBenefit4 == null)
                    {
                        result.put("success",0);
                        result.put("errMsg", "-1");
                    }
                    else
                    {
                        result.put("success",1);
                        result.put("errMsg",null);
                        result.put("envirBenefit", envirBenefit4);
                        result.put("socialBenefit", (SocialBenefit)score.get("socialBenefit"));
                        result.put("econoBenefit", (EconoBenefit)score.get("econoBenefit"));
                    }
                }
                else
                {
                    result.put("success",0);
                    result.put("errMsg", "1");
                }
                break;
        }
        if(projectAssignment != null && projectAssignment.getFinish() == 1)
            result.put("grade", projectAssignment.getGrade());
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



}
