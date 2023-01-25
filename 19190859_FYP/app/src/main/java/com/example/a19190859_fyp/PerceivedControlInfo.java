package com.example.a19190859_fyp;

public class PerceivedControlInfo {

    boolean tested;

    float inputTime;

    float inputDuration;

    float inputPressure;

    float timeBetweenInputs;

    public PerceivedControlInfo(boolean tested, float p, float d, float t, float i)
    {
        this.tested = tested;
        this.inputPressure = p;
        this.inputDuration = d;
        this.timeBetweenInputs = t;
        this.inputTime = i;
    }


}
