package com.whu.pojo;

import java.io.Serializable;
import java.util.List;

public class Expert implements Serializable
{
    private Long id;
    private String username;
    private String password;
    private String department;
    private String telephone;
    private String name;
    private String title;
    private List<Prize> prizeList;
    private List<Long> fProjectIdList;
    private List<Long> lProjectIdList;

    @Override
    public String toString()
    {
        return "Expert{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", telephone='" + telephone + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", prizeList=" + prizeList +
                ", fProjectIdList=" + fProjectIdList +
                ", lProjectIdList=" + lProjectIdList +
                '}';
    }

    public List<Long> getfProjectIdList()
    {
        return fProjectIdList;
    }

    public void setfProjectIdList(List<Long> fProjectIdList)
    {
        this.fProjectIdList = fProjectIdList;
    }

    public List<Long> getlProjectIdList()
    {
        return lProjectIdList;
    }

    public void setlProjectIdList(List<Long> lProjectIdList)
    {
        this.lProjectIdList = lProjectIdList;
    }

    public List<Prize> getPrizeList()
    {
        return prizeList;
    }

    public void setPrizeList(List<Prize> prizeList)
    {
        this.prizeList = prizeList;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

}
