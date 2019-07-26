package com.whu.pojo;

public class EnvirBenefit1 extends Benefit
{
    private float art;
    private float outdoorEnvir;
    private float resourceUtilization;
    private float indoorEnvir;
    private float constructionManagement;
    private float operationManagement;
    private float innovationEvaluation;

    @Override
    public String toString()
    {
        return "EnvirBenefit1{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", expertId=" + expertId +
                ", art=" + art +
                ", outdoorEnvir=" + outdoorEnvir +
                ", resourceUtilization=" + resourceUtilization +
                ", indoorEnvir=" + indoorEnvir +
                ", constructionManagement=" + constructionManagement +
                ", operationManagement=" + operationManagement +
                ", innovationEvaluation=" + innovationEvaluation +
                ", state=" + stateToString(state) +
                '}';
    }

    public float getArt()
    {
        return art;
    }

    public void setArt(float art)
    {
        this.art = art;
    }

    public float getResourceUtilization()
    {
        return resourceUtilization;
    }

    public void setResourceUtilization(float resourceUtilization)
    {
        this.resourceUtilization = resourceUtilization;
    }

    public float getIndoorEnvir()
    {
        return indoorEnvir;
    }

    public void setIndoorEnvir(float indoorEnvir)
    {
        this.indoorEnvir = indoorEnvir;
    }

    public float getOutdoorEnvir()
    {
        return outdoorEnvir;
    }

    public void setOutdoorEnvir(float outdoorEnvir)
    {
        this.outdoorEnvir = outdoorEnvir;
    }

    public float getConstructionManagement()
    {
        return constructionManagement;
    }

    public void setConstructionManagement(float constructionManagement)
    {
        this.constructionManagement = constructionManagement;
    }

    public float getOperationManagement()
    {
        return operationManagement;
    }

    public void setOperationManagement(float operationManagement)
    {
        this.operationManagement = operationManagement;
    }

    public float getInnovationEvaluation()
    {
        return innovationEvaluation;
    }

    public void setInnovationEvaluation(float innovationEvaluation)
    {
        this.innovationEvaluation = innovationEvaluation;
    }

}
