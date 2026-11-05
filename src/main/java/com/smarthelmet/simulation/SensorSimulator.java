
package com.smarthelmet.simulation;

import java.util.Random;

import com.smarthelmet.model.SensorData;

public class SensorSimulator {

    private Random random = new Random();

    public SensorData generateSensorData() {

        double temperature = 25 + random.nextInt(25);

        boolean impactDetected = random.nextInt(10) == 0;

        boolean motionDetected = random.nextBoolean();

        double gasLevel = 50 + random.nextInt(451);

        return new SensorData(
            temperature,
            impactDetected,
            motionDetected,
            gasLevel
        );
    }
}

