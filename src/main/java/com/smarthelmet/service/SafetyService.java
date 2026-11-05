package com.smarthelmet.service;

import com.smarthelmet.model.SensorData;

public class SafetyService {

    public String checkHelmetStatus(SensorData data) {

        // Emergency condition
        if (data.isImpactDetected()) {
            return "EMERGENCY";
        }

        // Warning conditions
        if (data.getTemperature() >= 40 ||
            data.getGasLevel() >= 300) {
            return "WARNING";
        }

        // Normal condition
        return "SAFE";
    }

    public String getAlertMessage(SensorData data) {

        if (data.isImpactDetected()) {
            return "Emergency! Helmet impact detected.";
        }

        if (data.getTemperature() >= 40) {
            return "Warning! High temperature detected.";
        }

        if (data.getGasLevel() >= 300) {
            return "Warning! Hazardous gas level detected.";
        }

        return "All conditions are normal.";
    }
}
