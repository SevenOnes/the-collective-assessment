package com.example.thecollectiveassessment.core.controller;

import com.example.thecollectiveassessment.core.dtos.GetPaginatedResponse;
import com.example.thecollectiveassessment.core.dtos.GetPlantByLocationDto;
import com.example.thecollectiveassessment.core.model.Plant;
import com.example.thecollectiveassessment.core.service.PlantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import utils.TestUtils;

import javax.management.InvalidAttributeValueException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlantControllerTest {

//    private static final String ENDPOINT = "/plant";

    @Mock
    PlantServiceImpl service;

    @InjectMocks
    PlantController controller;

    @Test
    public void testPing() throws Exception {
        assertEquals(controller.ping().getBody(), "Hello World");
    }

    @Test
    public void testGetTotalGenerationPower() {
        when(service.getTotalGenerationPower(anyString())).thenReturn(500L);
        assertEquals(500L, controller.getTotalGenerationPower("location").longValue());
    }

    @Test
    public void testGetPlantById() {
        Plant p = TestUtils.generateRandomPlant(3);
        when(service.getPlantById(anyString())).thenReturn(p);
        assertEquals(ResponseEntity.ok(p), controller.getPlantById("3"));
    }

    @Test
    public void testGetPlantsWithOrderAndPagination() throws InvalidAttributeValueException {
        List<Plant> plants = Arrays.asList(
                TestUtils.generateRandomPlant(1),
                TestUtils.generateRandomPlant(2),
                TestUtils.generateRandomPlant(3)
        );
        when(service.getOrderedPlants(any())).thenReturn(plants);
        assertEquals(ResponseEntity.ok(plants), controller.getPlantsWithOrderAndPagination(new GetPlantByLocationDto()));
    }

    @Test
    public void testGetPlantsByLocation() {
        List<Plant> plants = Arrays.asList(
                TestUtils.generateRandomPlant(1),
                TestUtils.generateRandomPlant(2),
                TestUtils.generateRandomPlant(3)
        );
        GetPaginatedResponse<Plant> response = new GetPaginatedResponse<Plant>()
                .setPlants(plants)
                .setOffset(5L)
                .setMoreExists(true);
        when(service.getPlantsByLocation(any())).thenReturn(response);
        assertEquals(ResponseEntity.ok(response), controller.getPlantsByLocation(new GetPlantByLocationDto()));
    }

    @Test
    public void testGetGenerationPercPlantByLocationResponseResponseEntity() {
        when(service.getPercentageProductionPlantsByLocation("location")).thenReturn(null);
        assertEquals(ResponseEntity.ok(null), controller.getGenerationPercPlantByLocationResponseResponseEntity("location"));
    }
}
