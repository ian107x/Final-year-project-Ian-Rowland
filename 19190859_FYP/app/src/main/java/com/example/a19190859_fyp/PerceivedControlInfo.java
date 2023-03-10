package com.example.a19190859_fyp;

public class PerceivedControlInfo {

    boolean tested;

    double inputTime;

    double inputDuration;

    double inputPressure;

    double timeBetweenInputs;

    double acceleration;

    public PerceivedControlInfo(boolean tested, double p, double d, double t, double i, double accel)
    {
        this.tested = tested;
        this.inputPressure = p;
        this.inputDuration = d;
        this.timeBetweenInputs = t;
        this.inputTime = i;
        this.acceleration = accel;
    }

    public boolean getTested()
    {
        return this.tested;
    }

    public double getInputTime()
    {
        return this.inputTime;

    }
    public double getInputDuration()
    {
        return this.inputDuration;
    }
    public double getInputPressure()
    {
        return this.inputPressure;
    }
    public double getTimeBetweenInputs()
    {
        return this.timeBetweenInputs;
    }
    public double getAccel()
    {
        return this.acceleration;
    }

}
