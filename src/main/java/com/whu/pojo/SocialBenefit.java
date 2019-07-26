package com.whu.pojo;

public class SocialBenefit extends Benefit
{
    private float effect;


    public float getEffect()
    {
        return effect;
    }

    public void setEffect(float effect)
    {
        this.effect = effect;
    }


    @Override
    public String toString()
    {
        return "SocialBenefit{" +
                "id=" + id +
                ", expertId=" + expertId +
                ", projectId=" + projectId +
                ", effect=" + effect +
                ", state=" + stateToString(state) +
                '}';
    }
}
