package com.pesados.purplepoint.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LocationController {
    public static boolean isLocationInA500MeterRadius(float actualLatitude, float actualLongitude, float latitude, float longitude) {
        float ky = 40000 / 360;
        float kx = (float) (Math.cos(Math.PI * actualLatitude / 180.0) * ky);
        float dx = Math.abs(actualLongitude - longitude) * kx;
        float dy = Math.abs(actualLatitude - latitude) * ky;
        return Math.sqrt(dx * dx + dy * dy) <= 0.5;
    }
}