
package com.smarthelmet.model;

public class SensorData {

    private double temperature;
    private boolean impactDetected;
    private boolean motionDetected;
    private double gasLevel;

    public SensorData(double temperature,
                      boolean impactDetected,
                      boolean motionDetected,
                      double gasLevel) {

        this.temperature = temperature;
        this.impactDetected = impactDetected;
        this.motionDetected = motionDetected;
        this.gasLevel = gasLevel;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isImpactDetected() {
        return impactDetected;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public double getGasLevel() {
        return gasLevel;
    }
}


