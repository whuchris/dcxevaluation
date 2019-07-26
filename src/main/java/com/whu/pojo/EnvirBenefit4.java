package com.whu.pojo;

public class EnvirBenefit4 extends Benefit
{
    private float culturalEnvir;
    private float physicalEnvir;
    private float decorationMaterial;
    private float decorationTechnology;

    public float getCulturalEnvir()
    {
        return culturalEnvir;
    }

    public void setCulturalEnvir(float culturalEnvir)
    {
        this.culturalEnvir = culturalEnvir;
    }

    public float getPhysicalEnvir()
    {
        return physicalEnvir;
    }

    public void setPhysicalEnvir(float physicalEnvir)
    {
        this.physicalEnvir = physicalEnvir;
    }

    public float getDecorationMaterial()
    {
        return decorationMaterial;
    }

    public void setDecorationMaterial(float decorationMaterial)
    {
        this.decorationMaterial = decorationMaterial;
    }

    public float getDecorationTechnology()
    {
        return decorationTechnology;
    }

    public void setDecorationTechnology(float decorationTechnology)
    {
        this.decorationTechnology = decorationTechnology;
    }

    @Override
    public String toString()
    {
        return "EnvirBenefit4{" +
                "cultural_envir=" + culturalEnvir +
                ", physicalEnvir=" + physicalEnvir +
                ", decorationMaterial=" + decorationMaterial +
                ", decorationTechnology=" + decorationTechnology +
                ", id=" + id +
                ", projectId=" + projectId +
                ", expertId=" + expertId +
                ", state=" + stateToString(state) +
                '}';
    }
}
