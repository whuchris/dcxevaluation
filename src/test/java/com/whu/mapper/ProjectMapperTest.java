package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Prize;
import com.whu.pojo.Project;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    PrizeMapper prizeMapper;

    @Test
    public void queryProjectById()
    {
        Project project = projectMapper.queryProjectById(3L);
        log.info("ProjectMapperTest.queryProjectById: " + project.toString());
    }



    @Test
    public void updateProject()
    {
        Project project = projectMapper.queryProjectById(1L);
        project.setAssessmentState(20);
        int code = projectMapper.updateProject(project);
        log.info("ProjectMapperTest.updateProject: " + code);
    }

    @Test
    public void queryProjectsByProjectIds()
    {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        List<Project> projects = projectMapper.queryProjectsByProjectIds(list, 45L,1);
        log.info("ProjectMapperTest.queryProjectsByProjectIds: " + projects);
    }

    @Test
    public void queryAllProjects()
    {
        List<Project> projects = projectMapper.queryAllProjects();
        log.info("ProjectMapperTest.queryAllProjects: " + projects);
    }

    @Test
    public void queryProjectsByState()
    {
        List<Project> projects = projectMapper.queryProjectsByState(1);
        log.info("ProjectMapperTest.queryProjectsByState: " + projects);
    }

    @Test
    public void queryUnassignedProjectsF()
    {
        List<Project> projects = projectMapper.queryUnassignedProjectsF();
        log.info("ProjectMapperTest.queryUnassignedProjectsF: " + projects);
    }

    @Test
    public void queryUnassignedProjectsL()
    {
        List<Project> projects = projectMapper.queryUnassignedProjectsL();
        log.info("ProjectMapperTest.queryUnassignedProjectsL: " + projects);
    }

    @Test
    public void fileTest()
    {
        File xml = new File("D:\\Programming\\IDEA\\fcxevaluation\\src\\test\\java\\com\\whu\\mapper\\final_project.xlsx");
        if(!xml.exists())
            log.info("ProjectMapperTest.fileTest: " + "file not exists");
        else
        {
            try
            {
                Workbook workbook = new XSSFWorkbook(xml);
                Sheet sheet = workbook.getSheet("Sheet1");
                int firstRowIndex = sheet.getFirstRowNum() + 1;
                int lastRowIndex = sheet.getLastRowNum();
                for(int i = firstRowIndex; i<= lastRowIndex ;i++)
                {
                    Project project = new Project();
                    Prize prize = new Prize();
                    Row row = sheet.getRow(i);
                    Cell id = row.getCell(0);
                    Cell fTypeString = row.getCell(2);
                    int fType = 0;
                    switch (fTypeString.toString())
                    {
                        case "产城类":
                            fType = 1;
                            break;
                        case "城更类":
                            fType = 2;
                            break;
                        case "居住类":
                            fType = 3;
                            break;
                        case "旅游类":
                            fType = 4;
                            break;
                        case "商办类":
                            fType = 5;
                            break;
                        case "文体类":
                            fType = 6;
                            break;
                        case "医养类":
                            fType = 7;
                            break;

                    }
                    Cell sTypeString = row.getCell(1);
                    int sType = 0;
                    switch (sTypeString.toString())
                    {
                        case "建筑":
                            sType = 1;
                            break;
                        case "规划":
                            sType = 2;
                            break;
                        case "景观":
                            sType = 3;
                            break;
                        case "室内":
                            sType = 4;
                            break;
                    }
                    Cell tTypeString = row.getCell(3);
                    int tType = 0;
                    switch (tTypeString.toString())
                    {
                        case "建成":
                            tType = 1;
                            break;
                        case "在建/方案":
                            tType = 2;
                            break;
                    }
                    Long prizeId = prizeMapper.queryPrizeIdByType(fType,sType,tType);
                    prize.setfType(fType);prize.setsType(sType);prize.settType(tType);prize.setId(prizeId);
                    Cell name = row.getCell(4);
                    Cell time = row.getCell(5);

                    project.setId(Long.valueOf(id.toString().split("\\.")[0]));
                    project.setPrize(prize);
                    project.setName(name.toString());
                    Date date = new Date(DateUtil.getJavaDate(Double.valueOf(time.toString())).getTime());
                    project.setApplicationTime(date);
                    project.setAssessmentState(1);
                    project.setPrizeClass(5);
                    projectMapper.insertProject(project,prizeId);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }
}
