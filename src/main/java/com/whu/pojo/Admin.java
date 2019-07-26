package com.whu.pojo;

public class Admin
{
    private Long id;
    private String username;
    private String password;
    private String name;
    private int autoAssign1;
    private int autoAssign2;

    public int getAutoAssign1()
    {
        return autoAssign1;
    }

    @Override
    public String toString()
    {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", autoAssign1=" + autoAssign1 +
                ", autoAssign2=" + autoAssign2 +
                '}';
    }

    public void setAutoAssign1(int autoAssign1)
    {
        this.autoAssign1 = autoAssign1;
    }

    public int getAutoAssign2()
    {
        return autoAssign2;
    }

    public void setAutoAssign2(int autoAssign2)
    {
        this.autoAssign2 = autoAssign2;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
