package com.pesados.purplepoint.api.tests.controller;


import com.pesados.purplepoint.api.controller.LocationController;
import com.pesados.purplepoint.api.model.location.Location;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnPointIsInA500MeterRadius() throws Exception {
        Location miCasa = new Location((float)41.447612, (float)2.224417, 100, 0);
        Location bar = new Location((float)41.447379, (float)2.226842, 100, 0);
        Location cancun = new Location((float)21.160510, (float)-86.842466, 100, 0);
        Location sarria = new Location((float)41.402899, (float)2.121561, 100, 0);

        Assert.assertTrue(LocationController.isLocationInA500MeterRadius(miCasa.getLatitude(), miCasa.getLongitude(), bar.getLatitude(), bar.getLongitude()));
        Assert.assertFalse(LocationController.isLocationInA500MeterRadius(cancun.getLatitude(), cancun.getLongitude(), bar.getLatitude(), bar.getLongitude()));
        Assert.assertFalse(LocationController.isLocationInA500MeterRadius(cancun.getLatitude(), cancun.getLongitude(), sarria.getLatitude(), sarria.getLongitude()));
    }

}