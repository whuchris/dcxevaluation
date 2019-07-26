package com.whu.pojo;

public class EnvirBenefit3 extends Benefit
{
    private float art;
    private float projectFunction;
    private float projectTechnology;
    private float envirFriendliness;

    public float getArt()
    {
        return art;
    }

    public void setArt(float art)
    {
        this.art = art;
    }

    public float getProjectFunction()
    {
        return projectFunction;
    }

    public void setProjectFunction(float projectFunction)
    {
        this.projectFunction = projectFunction;
    }

    public float getProjectTechnology()
    {
        return projectTechnology;
    }

    public void setProjectTechnology(float projectTechnology)
    {
        this.projectTechnology = projectTechnology;
    }

    public float getEnvirFriendliness()
    {
        return envirFriendliness;
    }

    public void setEnvirFriendliness(float envirFriendliness)
    {
        this.envirFriendliness = envirFriendliness;
    }

    @Override
    public String toString()
    {
        return "EnvirBenefit3{" +
                "art=" + art +
                ", projectFunction=" + projectFunction +
                ", projectTechonogy=" + projectTechnology +
                ", envirFriendliness=" + envirFriendliness +
                ", id=" + id +
                ", projectId=" + projectId +
                ", expertId=" + expertId +
                ", state=" + stateToString(state) +
                '}';
    }
}
