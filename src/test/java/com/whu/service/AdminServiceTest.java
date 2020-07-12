package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.mapper.*;
import com.whu.pojo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AdminServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    AdminService adminService;

    @Autowired
    EconoBenefitMapper econoBenefitMapper;

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
    ProjectMapper projectMapper;

    @Autowired
    PrizeMapper prizeMapper;

    @Test
    public void queryAdminById()
    {
        Admin admin = adminService.queryAdminById(1L);
        log.info("AdminMapperTest.AdminMapperTest: " + admin.toString());
    }

    @Test
    public void queryAdminByUsername()
    {
        Admin admin = adminService.queryAdminByUsername("admin");
        log.info("AdminMapperTest.queryAdminByUsername: " + admin.toString());
    }

    @Test
    public void sortByGrade()
    {
        /*List<Project> projectList = projectMapper.queryProjectsByStateWithOutInfo(1);
        List<Project> projectList2 = new ArrayList<>();
        Map<Float, Project> orderList = new HashMap<>();
        for(Project project: projectList)
        {
            List<EconoBenefit> econoBenefits =
                    econoBenefitMapper.queryScoresByProjectIdAndState(project.getId(), 1);
            float grade = 0.0f;
            for(EconoBenefit econoBenefit:econoBenefits)
            {
                grade += econoBenefit.getOperationPerformance();
            }
            if(grade / 5.0f == 82.4f)
                projectList2.add(project);
            orderList.put(grade,project);
        }
        Object[] keyArr = orderList.keySet().toArray();
        Arrays.sort(keyArr);
        for(int i = keyArr.length - 1; i > keyArr.length - 11; i--)
        {
            Project project = orderList.get(keyArr[i]);
            System.out.println(project.getId() + "\t" + project.getName() + "\t" + (float)keyArr[i] / 5.0f + "\t" );
        }
        System.out.println(projectList2);*/

       /*List<Project> projectList = projectMapper.queryProjectsByStateWithOutInfo(1);
        List<Project> projectList2 = new ArrayList<>();
        Map<Float, Project> orderList = new HashMap<>();
        for(Project project: projectList)
        {
            List<SocialBenefit> econoBenefits =
                    socialBenefitMapper.queryScoresByProjectIdAndState(project.getId(), 1);
            float grade = 0.0f;
            for(SocialBenefit econoBenefit:econoBenefits)
            {
                grade += econoBenefit.getEffect();
            }
            if(grade / 5.0f == 86.4f)
                projectList2.add(project);
            orderList.put(grade,project);
        }
        Object[] keyArr = orderList.keySet().toArray();
        Arrays.sort(keyArr);
        for(int i = keyArr.length - 1; i > keyArr.length - 11; i--)
        {
            Project project = orderList.get(keyArr[i]);
            System.out.println(project.getId() + "\t" + project.getName() + "\t" + (float)keyArr[i] / 5.0f + "\t" );
        }
        System.out.println(projectList2);*/

        /*List<Project> projectList = projectMapper.queryProjectsByState(1);
        List<Project> projectList2 = new ArrayList<>();
        Map<Float, Project> orderList = new HashMap<>();
        for(Project project: projectList)
        {
            switch(project.getPrize().getsType())
            {
                case 1: //建筑类
                    List<EnvirBenefit1> envirBenefit1s =
                            envirBenefit1Mapper.queryScoresByProjectIdAndState(project.getId(), 1);
                    float grade1 = 0.0f;
                    switch (project.getPrize().getfType())
                    {
                        case 1: //产城
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0469f
                                        + envirBenefit1.getResourceUtilization() * 0.1332f
                                        + envirBenefit1.getIndoorEnvir() * 0.0443f
                                        + envirBenefit1.getConstructionManagement() * 0.036f
                                        + envirBenefit1.getOperationManagement() * 0.0468f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0261f
                                        + envirBenefit1.getArt() * 0.0973f;
                            }
                            grade1 /= 0.4306f;
                            break;
                        case 2: //城更
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0424f
                                        + envirBenefit1.getResourceUtilization() * 0.1266f
                                        + envirBenefit1.getIndoorEnvir() * 0.0406f
                                        + envirBenefit1.getConstructionManagement() * 0.0382f
                                        + envirBenefit1.getOperationManagement() * 0.0437f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0266f
                                        + envirBenefit1.getArt() * 0.0954f;
                            }
                            grade1 /= 0.4135f;
                            break;
                        case 3: //居住
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0572f
                                        + envirBenefit1.getResourceUtilization() * 0.1533f
                                        + envirBenefit1.getIndoorEnvir() * 0.0599f
                                        + envirBenefit1.getConstructionManagement() * 0.0489f
                                        + envirBenefit1.getOperationManagement() * 0.0462f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0213f
                                        + envirBenefit1.getArt() * 0.1121f;
                            }
                            grade1 /= 0.4989f;
                            break;
                        case 4: //旅游
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.053f
                                        + envirBenefit1.getResourceUtilization() * 0.1509f
                                        + envirBenefit1.getIndoorEnvir() * 0.0482f
                                        + envirBenefit1.getConstructionManagement() * 0.0409f
                                        + envirBenefit1.getOperationManagement() * 0.0457f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0201f
                                        + envirBenefit1.getArt() * 0.1162f;
                            }
                            grade1 /= 0.475f;
                            break;
                        case 5: //商办
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0435f
                                        + envirBenefit1.getResourceUtilization() * 0.1271f
                                        + envirBenefit1.getIndoorEnvir() * 0.0526f
                                        + envirBenefit1.getConstructionManagement() * 0.0357f
                                        + envirBenefit1.getOperationManagement() * 0.0456f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0206f
                                        + envirBenefit1.getArt() * 0.0916f;
                            }
                            grade1 /= 0.4167f;
                            break;
                        case 6: //文体
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0519f
                                        + envirBenefit1.getResourceUtilization() * 0.1509f
                                        + envirBenefit1.getIndoorEnvir() * 0.0463f
                                        + envirBenefit1.getConstructionManagement() * 0.0446f
                                        + envirBenefit1.getOperationManagement() * 0.0475f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0122f
                                        + envirBenefit1.getArt() * 0.0929f;
                            }
                            grade1 /= 0.4463f;
                            break;
                        case 7: //医养
                            for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                            {
                                grade1 = grade1
                                        + envirBenefit1.getOutdoorEnvir() * 0.0452f
                                        + envirBenefit1.getResourceUtilization() * 0.133f
                                        + envirBenefit1.getIndoorEnvir() * 0.0494f
                                        + envirBenefit1.getConstructionManagement() * 0.0378f
                                        + envirBenefit1.getOperationManagement() * 0.0449f
                                        + envirBenefit1.getInnovationEvaluation() * 0.0246f
                                        + envirBenefit1.getArt() * 0.0904f;
                            }
                            grade1 /= 0.4253f;
                            break;
                    }

                    orderList.put(grade1,project);
                    break;
                case 2: //规划类
                    List<EnvirBenefit2> envirBenefit2s =
                            envirBenefit2Mapper.queryScoresByProjectIdAndState(project.getId(), 1);
                    float grade2 = 0.0f;
                    switch (project.getPrize().getfType())
                    {
                        case 1: //产城
                            for(EnvirBenefit2 envirBenefit2:envirBenefit2s)
                            {
                                grade2 = grade2
                                        + envirBenefit2.getLandUsing() * 0.059f
                                        + envirBenefit2.getEnvir() * 0.105f
                                        + envirBenefit2.getGreenTransportation() * 0.095f
                                        + envirBenefit2.getInformationManagement() * 0.063f
                                        + envirBenefit2.getArt() * 0.092f;
                            }
                            grade2 /= 0.414f;
                            break;
                        case 2: //城更
                            for(EnvirBenefit2 envirBenefit2:envirBenefit2s)
                            {
                                grade2 = grade2
                                        + envirBenefit2.getLandUsing() * 0.051f
                                        + envirBenefit2.getEnvir() * 0.094f
                                        + envirBenefit2.getGreenTransportation() * 0.086f
                                        + envirBenefit2.getInformationManagement() * 0.058f
                                        + envirBenefit2.getArt() * 0.106f;
                            }
                            grade2 /= 0.395f;
                            break;
                        case 3: //居住
                            for(EnvirBenefit2 envirBenefit2:envirBenefit2s)
                            {
                                grade2 = grade2
                                        + envirBenefit2.getLandUsing() * 0.0672f
                                        + envirBenefit2.getEnvir() * 0.1161f
                                        + envirBenefit2.getGreenTransportation() * 0.0983f
                                        + envirBenefit2.getInformationManagement() * 0.0603f
                                        + envirBenefit2.getArt() * 0.105f;
                            }
                            grade2 /= 0.4469f;
                            break;
                        case 4: //旅游
                            for(EnvirBenefit2 envirBenefit2:envirBenefit2s)
                            {
                                grade2 = grade2
                                        + envirBenefit2.getLandUsing() * 0.0678f
                                        + envirBenefit2.getEnvir() * 0.1226f
                                        + envirBenefit2.getGreenTransportation() * 0.1081f
                                        + envirBenefit2.getInformationManagement() * 0.056f
                                        + envirBenefit2.getArt() * 0.112f;
                            }
                            grade2 /= 0.4665f;
                            break;
                        case 5: //商办
                            break;
                        case 6: //文体
                            break;
                        case 7: //医养
                            for(EnvirBenefit2 envirBenefit2:envirBenefit2s)
                            {
                                grade2 = grade2
                                        + envirBenefit2.getLandUsing() * 0.0551f
                                        + envirBenefit2.getEnvir() * 0.1078f
                                        + envirBenefit2.getGreenTransportation() * 0.0945f
                                        + envirBenefit2.getInformationManagement() * 0.0656f
                                        + envirBenefit2.getArt() * 0.1007f;
                            }
                            grade2 /= 0.4237f;
                            break;
                    }
                    orderList.put(grade2,project);
                    break;
                case 3: //景观类
                    List<EnvirBenefit3> envirBenefit3s =
                            envirBenefit3Mapper.queryScoresByProjectIdAndState(project.getId(), 1);
                    float grade3 = 0.0f;
                    switch (project.getPrize().getfType())
                    {
                        case 1: //产城
                            break;
                        case 2: //城更
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1151f
                                        + envirBenefit3.getProjectTechnology() * 0.0959f
                                        + envirBenefit3.getArt() * 0.1112f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1684f;
                            }
                            grade3 /= 0.4906f;
                            break;
                        case 3: //居住
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1356f
                                        + envirBenefit3.getProjectTechnology() * 0.1036f
                                        + envirBenefit3.getArt() * 0.1313f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1989f;
                            }
                            grade3 /= 0.5694f;
                            break;
                        case 4: //旅游
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1371f
                                        + envirBenefit3.getProjectTechnology() * 0.0937f
                                        + envirBenefit3.getArt() * 0.1486f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1984f;
                            }
                            grade3 /= 0.5778f;
                            break;
                        case 5: //商办
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1156f
                                        + envirBenefit3.getProjectTechnology() * 0.0928f
                                        + envirBenefit3.getArt() * 0.1203f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1641f;
                            }
                            grade3 /= 0.4928f;
                            break;
                        case 6: //文体
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1193f
                                        + envirBenefit3.getProjectTechnology() * 0.0816f
                                        + envirBenefit3.getArt() * 0.1202f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1733f;
                            }
                            grade3 /= 0.4944f;
                            break;
                        case 7: //医养
                            for(EnvirBenefit3 envirBenefit3:envirBenefit3s)
                            {
                                grade3 = grade3
                                        + envirBenefit3.getProjectFunction() * 0.1269f
                                        + envirBenefit3.getProjectTechnology() * 0.0873f
                                        + envirBenefit3.getArt() * 0.1014f
                                        + envirBenefit3.getEnvirFriendliness() * 0.1747f;
                            }
                            grade3 /= 0.4903f;
                            break;
                    }

                    orderList.put(grade3,project);
                    break;
                case 4: //室内类
                    List<EnvirBenefit4> envirBenefit4s =
                            envirBenefit4Mapper.queryScoresByProjectIdAndState(project.getId(), 1);
                    float grade4 = 0.0f;
                    switch (project.getPrize().getfType())
                    {
                        case 1: //产城
                            break;
                        case 2: //城更
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1323f
                                        + envirBenefit4.getCulturalEnvir() * 0.1509f
                                        + envirBenefit4.getDecorationMaterial() * 0.093f
                                        + envirBenefit4.getDecorationTechnology() * 0.1333f;
                            }
                            grade4 /= 0.5125f;
                            break;
                        case 3: //居住
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1503f
                                        + envirBenefit4.getCulturalEnvir() * 0.1309f
                                        + envirBenefit4.getDecorationMaterial() * 0.1043f
                                        + envirBenefit4.getDecorationTechnology() * 0.138f;
                            }
                            grade4 /= 0.5235f;
                            break;
                        case 4: //旅游
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1431f
                                        + envirBenefit4.getCulturalEnvir() * 0.1557f
                                        + envirBenefit4.getDecorationMaterial() * 0.105f
                                        + envirBenefit4.getDecorationTechnology() * 0.1306f;
                            }
                            grade4 /= 0.5344f;
                            break;
                        case 5: //商办
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1355f
                                        + envirBenefit4.getCulturalEnvir() * 0.1325f
                                        + envirBenefit4.getDecorationMaterial() * 0.089f
                                        + envirBenefit4.getDecorationTechnology() * 0.123f;
                            }
                            grade4 /= 0.48f;
                            break;
                        case 6: //文体
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1435f
                                        + envirBenefit4.getCulturalEnvir() * 0.1333f
                                        + envirBenefit4.getDecorationMaterial() * 0.0948f
                                        + envirBenefit4.getDecorationTechnology() * 0.1347f;
                            }
                            grade4 /= 0.5063f;
                            break;
                        case 7: //医养
                            for(EnvirBenefit4 envirBenefit4:envirBenefit4s)
                            {
                                grade4 = grade4
                                        + envirBenefit4.getPhysicalEnvir() * 0.1445f
                                        + envirBenefit4.getCulturalEnvir() * 0.1316f
                                        + envirBenefit4.getDecorationMaterial() * 0.0909f
                                        + envirBenefit4.getDecorationTechnology() * 0.1394f;
                            }
                            grade4 /= 0.5062f;
                            break;
                    }

                    orderList.put(grade4,project);
                    break;

            }
        }
        Object[] keyArr = orderList.keySet().toArray();
        Arrays.sort(keyArr);
        for(int i = keyArr.length - 1; i > keyArr.length - 11; i--)
        {
            Project project = orderList.get(keyArr[i]);
            System.out.println(project.getId() + "\t" + project.getName() + "\t" + (float)keyArr[i] / 5.0f + "\t" );
        }
        System.out.println(projectList2);*/

        List<Project> projectList = projectMapper.queryProjectsByState(1);
        List<Project> projectList2 = new ArrayList<>();
        Map<Float, Project> orderList = new HashMap<>();
        for(Project project: projectList)
        {
            if(project.getPrize().getsType() == 1)
            {
                List<EnvirBenefit1> envirBenefit1s =
                        envirBenefit1Mapper.queryScoresByProjectIdAndState(project.getId(), 1);
                float grade1 = 0.0f;
                for(EnvirBenefit1 envirBenefit1:envirBenefit1s)
                    grade1 += envirBenefit1.getInnovationEvaluation();

                orderList.put(grade1,project);
                if(grade1 / 5.0f == 86.0f )
                    projectList2.add(project);
            }
        }
        Object[] keyArr = orderList.keySet().toArray();
        Arrays.sort(keyArr);
        for(int i = keyArr.length - 1; i > keyArr.length - 11; i--)
        {
            Project project = orderList.get(keyArr[i]);
            System.out.println(project.getId() + "\t" + project.getName() + "\t" + (float)keyArr[i] / 5.0f + "\t" );
        }
        System.out.println(projectList2);

    }
}
