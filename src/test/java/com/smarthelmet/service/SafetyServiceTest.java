package com.smarthelmet.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smarthelmet.model.SensorData;

public class SafetyServiceTest {

    SafetyService service = new SafetyService();

    @Test
    public void testSafeCondition() {

        SensorData data =
            new SensorData(30, false, true, 100);

        assertEquals(
            "SAFE",
            service.checkHelmetStatus(data)
        );
    }

    @Test
    public void testHighTemperature() {

        SensorData data =
            new SensorData(45, false, true, 100);

        assertEquals(
            "WARNING",
            service.checkHelmetStatus(data)
        );
    }

    @Test
    public void testHighGasLevel() {

        SensorData data =
            new SensorData(30, false, true, 400);

        assertEquals(
            "WARNING",
            service.checkHelmetStatus(data)
        );
    }

    @Test
    public void testImpactEmergency() {

        SensorData data =
            new SensorData(30, true, true, 100);

        assertEquals(
            "EMERGENCY",
            service.checkHelmetStatus(data)
        );
    }
}
