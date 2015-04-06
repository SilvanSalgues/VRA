package com.example.darren.VRA.Sensor;

public class LowPassFilter {
    /*
     * time smoothing constant for low-pass filter 0 ? alpha ? 1 ; a smaller
     * /Low-pass_filter#Discrete-time_realization
     */
    float ALPHA = 0f;
    float lastOutput = 0;

    public LowPassFilter(float ALPHA) {
        this.ALPHA = ALPHA;
    }

    public float lowPass(float input) {
        if (Math.abs(input - lastOutput) > 170) {
            lastOutput = input;
            return lastOutput;
        }
        lastOutput = lastOutput + ALPHA * (input - lastOutput);
        return lastOutput;
    }
}