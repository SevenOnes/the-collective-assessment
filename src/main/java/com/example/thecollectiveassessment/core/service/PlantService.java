package com.example.thecollectiveassessment.core.service;

import com.example.thecollectiveassessment.core.dtos.GetGenerationPercPlantByLocationResponse;
import com.example.thecollectiveassessment.core.dtos.GetPaginatedResponse;
import com.example.thecollectiveassessment.core.dtos.GetPlantByLocationDto;
import com.example.thecollectiveassessment.core.model.Plant;

import javax.management.InvalidAttributeValueException;
import java.util.List;

public interface PlantService {

    Long getTotalGenerationPower(String location);

    Plant getPlantById(String id);

    List<Plant> getOrderedPlants(GetPlantByLocationDto dto) throws InvalidAttributeValueException;

    GetPaginatedResponse<Plant> getPlantsByLocation(GetPlantByLocationDto dto);

    GetGenerationPercPlantByLocationResponse getPercentageProductionPlantsByLocation(String location);

    void seed();
}
