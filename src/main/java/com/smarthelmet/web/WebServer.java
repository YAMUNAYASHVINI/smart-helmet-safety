package com.smarthelmet.web;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import com.smarthelmet.model.SensorData;
import com.smarthelmet.simulation.SensorSimulator;
import com.smarthelmet.service.SafetyService;

public class WebServer {

    public static void main(String[] args) throws IOException {

        SensorSimulator simulator = new SensorSimulator();
        SafetyService safetyService = new SafetyService();

        // Create web server
        HttpServer server = HttpServer.create(
            new InetSocketAddress(8081), 0
        );

        // Serve website
        server.createContext("/", exchange -> {

            if (!exchange.getRequestURI().getPath().equals("/")
                && !exchange.getRequestURI().getPath().equals("/index.html")) {

                sendResponse(exchange, 404, "Page Not Found", "text/plain");
                return;
            }

            try (InputStream input = WebServer.class
                    .getResourceAsStream("/static/index.html")) {

                if (input == null) {
                    sendResponse(exchange, 404,
                        "index.html not found. Check src/main/resources/static/",
                        "text/plain");
                    return;
                }

                byte[] html = input.readAllBytes();

                exchange.getResponseHeaders().set(
                    "Content-Type", "text/html; charset=UTF-8"
                );

                exchange.sendResponseHeaders(200, html.length);

                try (OutputStream output = exchange.getResponseBody()) {
                    output.write(html);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Sensor API
        server.createContext("/api/sensor", exchange -> {

            SensorData data = simulator.generateSensorData();

            String status = safetyService.checkHelmetStatus(data);

            String alert = safetyService.getAlertMessage(data);

            String json = "{"
                + "\"temperature\":" + data.getTemperature() + ","
                + "\"impactDetected\":" + data.isImpactDetected() + ","
                + "\"motionDetected\":" + data.isMotionDetected() + ","
                + "\"gasLevel\":" + data.getGasLevel() + ","
                + "\"status\":\"" + status + "\","
                + "\"alert\":\"" + alert + "\""
                + "}";

            sendResponse(exchange, 200, json, "application/json");
        });

        server.setExecutor(null);
        server.start();

        System.out.println("==================================");
        System.out.println(" SMART HELMET WEB SERVER STARTED");
        System.out.println("==================================");
        System.out.println("Website: http://localhost:8081");
        System.out.println("Sensor API: http://localhost:8081/api/sensor");
    }

    private static void sendResponse(
            HttpExchange exchange,
            int status,
            String response,
            String contentType) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
            "Content-Type", contentType + "; charset=UTF-8"
        );

        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}

