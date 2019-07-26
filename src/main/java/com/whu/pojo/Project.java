package com.whu.pojo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Project
{
    private Long id;
    private Prize prize;
    private int assessmentState;
    private int prizeClass;
    private Date applicationTime;
    private String name;
    private float fGrade = -1.0f;
    private float lGrade = -1.0f;
    private List<String> fExpertName;
    private List<String> lExpertName;
    private int finish;

    public int getFinish()
    {
        return finish;
    }

    public void setFinish(int finish)
    {
        this.finish = finish;
    }

    public float getfGrade()
    {
        return fGrade;
    }

    public List<String> getfExpertName()
    {
        return fExpertName;
    }

    public void setfExpertName(List<String> fExpertName)
    {
        this.fExpertName = fExpertName;
    }

    public List<String> getlExpertName()
    {
        return lExpertName;
    }

    public void setlExpertName(List<String> lExpertName)
    {
        this.lExpertName = lExpertName;
    }

    @Override
    public String toString()
    {
        return "Project{" +
                "id=" + id +
                ", prize=" + prize +
                ", assessmentState=" + assessmentState +
                ", prizeClass=" + prizeClass +
                ", applicationTime=" + applicationTime +
                ", name='" + name + '\'' +
                ", fGrade=" + fGrade +
                ", lGrade=" + lGrade +
                ", fExpertName='" + fExpertName + '\'' +
                ", lExpertName='" + lExpertName + '\'' +
                ", finish='" + finish + '\'' +
                '}';
    }

    public void setfGrade(float fGrade)
    {
        this.fGrade = fGrade;
    }

    public float getlGrade()
    {
        return lGrade;
    }

    public void setlGrade(float lGrade)
    {
        this.lGrade = lGrade;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Prize getPrize()
    {
        return prize;
    }

    public void setPrize(Prize prize)
    {
        this.prize = prize;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAssessmentState()
    {
        return assessmentState;
    }

    public void setAssessmentState(int assessmentState)
    {
        this.assessmentState = assessmentState;
    }

    public int getPrizeClass()
    {
        return prizeClass;
    }

    public void setPrizeClass(int prizeClass)
    {
        this.prizeClass = prizeClass;
    }

    public Date getApplicationTime()
    {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime)
    {
        this.applicationTime = applicationTime;
    }

    public static String assessmentStateToString(int state)
    {
        switch (state)
        {
            case 0:
                return "待初评";
            case 1:
                return "初评中";
            case 2:
                return "初评完成";
            case 20:
                return "待会评";
            case 21:
                return "会评中";
            case 22:
                return "会评完成";
            case 23:
                return "会评晋级";
            case 24:
                return "会评淘汰";
            case 3:
                return "初评晋级";
            case 4:
                return "初评淘汰";
             default:
                return "待初评";
        }
    }

    public static String prizeClassToString(int prizeClass)
    {
        switch (prizeClass)
        {
            case 1:
                return "一等奖";
            case 2:
                return "二等奖";
            case 3:
                return "三等奖";
            case 4:
                return "优秀奖";
             default:
                return "待定";
        }
    }

    public int myfType()
    {
        return prize.getfType();
    }

    public int mysType()
    {
        return prize.getsType();
    }

    public int mytType()
    {
        return prize.gettType();
    }

    public static String applicationDateToString(Date date)
    {
        if(date == null)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").toString();
        else
            return date.toString();
    }
}
