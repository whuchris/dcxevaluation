package com.whu.pojo;

public class Prize
{
    private Long id;
    private int fType;
    private int sType;
    private int tType;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Prize{" +
                "id=" + id +
                ", fType=" + fTypeToString(fType) +
                ", sType=" + sTypeToString(sType) +
                ", tType=" + tTypeToString(tType) +
                '}';
    }

    public int getfType()
    {
        return fType;
    }

    public void setfType(int fType)
    {
        this.fType = fType;
    }

    public int getsType()
    {
        return sType;
    }

    public void setsType(int sType)
    {
        this.sType = sType;
    }

    public int gettType()
    {
        return tType;
    }

    public void settType(int tType)
    {
        this.tType = tType;
    }

    public static String fTypeToString(int fType)
    {
        switch (fType)
        {
            case 1:
                return "产城类";
            case 2:
                return "城更类";
            case 3:
                return "居住类";
            case 4:
                return "旅游类";
            case 5:
                return "商办类";
            case 6:
                return "文体类";
            case 7:
                return "医养类";
            default:
                return null;
        }
    }

    public static String sTypeToString(int sType)
    {
        switch (sType)
        {
            case 1:
                return "建筑";
            case 2:
                return "规划";
            case 3:
                return "景观";
            case 4:
                return "室内";
            default:
                return null;
        }
    }

    public static String tTypeToString(int tType)
    {
        switch (tType)
        {
            case 1:
                return "建成";
            case 2:
                return "在建";
            default:
                return null;
        }
    }
}
