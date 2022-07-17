package com.example.thecollectiveassessment.core.controller;

import com.example.thecollectiveassessment.core.dtos.GetGenerationPercPlantByLocationResponse;
import com.example.thecollectiveassessment.core.dtos.GetPaginatedResponse;
import com.example.thecollectiveassessment.core.dtos.GetPlantByLocationDto;
import com.example.thecollectiveassessment.core.model.Plant;
import com.example.thecollectiveassessment.core.service.PlantService;
import com.example.thecollectiveassessment.core.service.PlantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.util.List;

@RestController()
@RequestMapping("/plant")
public class PlantController {

    private final PlantService service;

    @Autowired
    public PlantController(PlantServiceImpl service){
        this.service = service;
    }

    @GetMapping("/ping")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/seed")
    public void seed() {
        service.seed();
    }

    @GetMapping("/total/{location}")
    public Long getTotalGenerationPower(@PathVariable String location){
        return service.getTotalGenerationPower(location);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable String id){
        return ResponseEntity.ok(service.getPlantById(id));
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<Plant>> getPlantsWithOrderAndPagination(GetPlantByLocationDto request) throws InvalidAttributeValueException {
        return ResponseEntity.ok(service.getOrderedPlants(request));
    }

    @GetMapping()
    public ResponseEntity<GetPaginatedResponse<Plant>> getPlantsByLocation(GetPlantByLocationDto request) {
        return ResponseEntity.ok(service.getPlantsByLocation(request));
    }

    @GetMapping("/percentage/{location}")
    public ResponseEntity<GetGenerationPercPlantByLocationResponse> getGenerationPercPlantByLocationResponseResponseEntity(@PathVariable String location) {
        return ResponseEntity.ok(service.getPercentageProductionPlantsByLocation(location));
    }
}
