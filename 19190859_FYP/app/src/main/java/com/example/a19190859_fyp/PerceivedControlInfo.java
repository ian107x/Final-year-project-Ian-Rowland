package com.example.a19190859_fyp;

public class PerceivedControlInfo {

    boolean tested;

    double inputTime;

    double inputDuration;

    double inputPressure;

    double timeBetweenInputs;

    public PerceivedControlInfo(boolean tested, double p, double d, double t, double i)
    {
        this.tested = tested;
        this.inputPressure = p;
        this.inputDuration = d;
        this.timeBetweenInputs = t;
        this.inputTime = i;
    }


}
