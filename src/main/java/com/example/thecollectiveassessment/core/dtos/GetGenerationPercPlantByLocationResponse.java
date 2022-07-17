package com.example.thecollectiveassessment.core.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class GetGenerationPercPlantByLocationResponse {
    Map<String, Double> percentages;

    String location;

    Long totalGeneration;

    Long totalPlants;
}
