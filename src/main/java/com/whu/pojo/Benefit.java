package com.whu.pojo;

public class Benefit
{
    protected Long id;
    protected Long projectId;
    protected Long expertId;
    protected int state;

    public Long getId()
    {
        return id;
    }

    public static String stateToString(int state)
    {
        switch (state)
        {
            case 1:
                return "初评";
            case 2:
                return "会评";
            default:
                return null;
        }
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getExpertId()
    {
        return expertId;
    }

    public void setExpertId(Long expertId)
    {
        this.expertId = expertId;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
}
