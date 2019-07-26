package com.whu.pojo;

import java.util.List;

public class ExpertType
{
    private List<Expert> expertList;
    private List<Prize> prizeList;

    @Override
    public String toString()
    {
        return "ExpertType{" +
                "expertList=" + expertList +
                ", prizeList=" + prizeList +
                '}';
    }

    public List<Expert> getExpertList()
    {
        return expertList;
    }

    public void setExpertList(List<Expert> expertList)
    {
        this.expertList = expertList;
    }

    public List<Prize> getPrizeList()
    {
        return prizeList;
    }

    public void setPrizeList(List<Prize> prizeList)
    {
        this.prizeList = prizeList;
    }

}
