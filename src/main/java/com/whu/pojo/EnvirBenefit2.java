package com.whu.pojo;

public class EnvirBenefit2 extends Benefit
{
    private float art;
    private float landUsing;
    private float envir;
    private float greenTransportation;
    private float informationManagement;

    public float getArt()
    {
        return art;
    }

    public void setArt(float art)
    {
        this.art = art;
    }

    public float getLandUsing()
    {
        return landUsing;
    }

    public void setLandUsing(float landUsing)
    {
        this.landUsing = landUsing;
    }

    public float getGreenTransportation()
    {
        return greenTransportation;
    }

    public void setGreenTransportation(float greenTransportation)
    {
        this.greenTransportation = greenTransportation;
    }

    public float getEnvir()
    {
        return envir;
    }

    public void setEnvir(float envir)
    {
        this.envir = envir;
    }

    public float getInformationManagement()
    {
        return informationManagement;
    }

    public void setInformationManagement(float informationManagement)
    {
        this.informationManagement = informationManagement;
    }

    @Override
    public String toString()
    {
        return "EnvirBenefit2{" +
                "art=" + art +
                ", landUsing=" + landUsing +
                ", greenTransportaion=" + greenTransportation +
                ", envir=" + envir +
                ", informationManagement=" + informationManagement +
                ", id=" + id +
                ", projectId=" + projectId +
                ", expertId=" + expertId +
                ", state=" + stateToString(state) +
                '}';
    }
}
