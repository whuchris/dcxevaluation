package com.whu.pojo;

public class EconoBenefit extends Benefit
{
    private float operationPerformance;

    @Override
    public String toString()
    {
        return "EconoBenefit{" +
                "id=" + id +
                ", expertId=" + expertId +
                ", projectId=" + projectId +
                ", operationPerformance=" + operationPerformance +
                ", state=" + stateToString(state) +
                '}';
    }

    public float getOperationPerformance()
    {
        return operationPerformance;
    }

    public void setOperationPerformance(float operationPerformance)
    {
        this.operationPerformance = operationPerformance;
    }
}

