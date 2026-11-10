package com.smarthelmet;

import com.smarthelmet.model.SensorData;
import com.smarthelmet.service.SafetyService;
import com.smarthelmet.simulation.SensorSimulator;

public class MainApplication {

    public static void main(String[] args) {

        SensorSimulator simulator = new SensorSimulator();

        SafetyService safetyService = new SafetyService();
     // Read Jenkins build parameters
        double temperature = Double.parseDouble(
            System.getProperty("TEMPERATURE", "30")
        );

        String helmetStatus = System.getProperty(
            "HELMET_STATUS", "SAFE"
        );

        // Simulate sensor values based on Jenkins parameters
        boolean impact = helmetStatus.equalsIgnoreCase("EMERGENCY");

        double gasLevel = helmetStatus.equalsIgnoreCase("WARNING") ? 350 : 100;

        SensorData jenkinsData = new SensorData(
            temperature, impact, false, gasLevel
        );

        // Process safety status
        String actualStatus = safetyService.checkHelmetStatus(jenkinsData);
        String alert = safetyService.getAlertMessage(jenkinsData);

        System.out.println("\n===== JENKINS PARAMETERIZED SENSOR OUTPUT =====");
        System.out.println("Temperature: " + temperature + " °C");
        System.out.println("Selected Status: " + helmetStatus);
        System.out.println("Calculated Status: " + actualStatus);
        System.out.println("Alert: " + alert);
        System.out.println("==================================");
        System.out.println(" SMART HELMET SAFETY MONITORING ");
        System.out.println("==================================");

        for (int i = 1; i <= 5; i++) {

            SensorData data = simulator.generateSensorData();

            String status = safetyService.checkHelmetStatus(data);

            String parameterAlert = safetyService.getAlertMessage(jenkinsData);

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

            System.out.println("Alert: " + parameterAlert);
        }
    }
}

