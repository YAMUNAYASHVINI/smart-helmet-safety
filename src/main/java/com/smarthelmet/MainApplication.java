package com.smarthelmet;

import com.smarthelmet.model.SensorData;
import com.smarthelmet.simulation.SensorSimulator;
import com.smarthelmet.service.SafetyService;

public class MainApplication {

    public static void main(String[] args) {

        SensorSimulator simulator = new SensorSimulator();

        SafetyService safetyService = new SafetyService();

        System.out.println("==================================");
        System.out.println(" SMART HELMET SAFETY MONITORING ");
        System.out.println("==================================");

        for (int i = 1; i <= 5; i++) {

            SensorData data = simulator.generateSensorData();

            String status = safetyService.checkHelmetStatus(data);

            String alert = safetyService.getAlertMessage(data);

            System.out.println("\nReading " + i);

            System.out.println("Temperature: "
                    + data.getTemperature() + " C");

            System.out.println("Impact Detected: "
                    + data.isImpactDetected());

            System.out.println("Motion Detected: "
                    + data.isMotionDetected());

            System.out.println("Gas Level: "
                    + data.getGasLevel() + " ppm");

            System.out.println("Helmet Status: " + status);

            System.out.println("Alert: " + alert);
        }
    }
}

