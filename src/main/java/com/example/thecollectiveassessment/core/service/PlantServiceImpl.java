package com.example.thecollectiveassessment.core.service;

import com.example.thecollectiveassessment.core.dtos.GetGenerationPercPlantByLocationResponse;
import com.example.thecollectiveassessment.core.dtos.GetPaginatedResponse;
import com.example.thecollectiveassessment.core.dtos.GetPlantByLocationDto;
import com.example.thecollectiveassessment.core.enums.OrderBy;
import com.example.thecollectiveassessment.core.miscellaneous.SampleDataLoader;
import com.example.thecollectiveassessment.core.model.Plant;
import com.example.thecollectiveassessment.core.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository repository;

    @Autowired
    public PlantServiceImpl(PlantRepository repository){
        this.repository = repository;
    }

    @Override
    public void seed(){
        SampleDataLoader.readFromFileAndSaveToDB();
    }

    @Override
    public Long getTotalGenerationPower(String location){
        Long amount = repository.getTotalPowerGenerationByLocation(location);
        if(amount == null){
            return 0L;
        }
        return repository.getTotalPowerGenerationByLocation(location);
    }

    @Override
    public Plant getPlantById(String id){
        return repository.getPlantById(Integer.parseInt(id));
    }

    @Override
    public List<Plant> getOrderedPlants(GetPlantByLocationDto dto) throws InvalidAttributeValueException {
        if(dto.getOrderBy() == OrderBy.top){
            return repository.getTopPlants(dto.getLocation(), dto.getLimit());
        } else if (dto.getOrderBy() == OrderBy.bottom) {
            return repository.getBottomPlants(dto.getLocation(), dto.getLimit());
        }
        throw new InvalidAttributeValueException("Invalid order by property");
    }

    @Override
    public GetPaginatedResponse<Plant> getPlantsByLocation(GetPlantByLocationDto dto) {
        List<Plant> plants = repository.getPlantsByLocation(dto.getLocation(),dto.getLimit()+1, dto.getOffset());

        GetPaginatedResponse<Plant> response = new GetPaginatedResponse<>();

        if(plants.size() > dto.getLimit()){
            response.setMoreExists(true);
            response.setOffset((long)(dto.getOffset() + plants.size() - 1));
            response.setPlants(plants.subList(0, plants.size()-1));
        } else {
            response.setOffset((long)(dto.getOffset() + plants.size()));
            response.setPlants(plants);
        }

        return response;
    }

    @Override
    public GetGenerationPercPlantByLocationResponse getPercentageProductionPlantsByLocation(String location) {
        Long totalPlants = repository.getTotalActivePlantsByLocation(location);
        List<Plant> plants = repository.getPlantsByLocation(location);
        Long totalGeneration = repository.getTotalPowerGenerationByLocation(location);

        GetGenerationPercPlantByLocationResponse response = new GetGenerationPercPlantByLocationResponse();
        response.setLocation(location);
        response.setTotalPlants(totalPlants);
        response.setTotalGeneration(totalGeneration);
        response.setPercentages(new ConcurrentHashMap<>());
        calculatePercentages(plants, (ConcurrentHashMap<String, Double>)(response.getPercentages()), totalGeneration);
        return response;
    }

    private void calculatePercentages(List<Plant> plants, ConcurrentHashMap<String, Double> map, Long totalGeneration) {
        ExecutorService executor = Executors.newFixedThreadPool(30);
        for(Plant plant: plants){
            executor.execute(() -> map.put(plant.getId() + "#" + plant.getName(), Math.round(100.0 * 100 * plant.getGenerationAmount()/totalGeneration)/100.0));
        }
        try {
            executor.shutdown();
            while(!executor.awaitTermination(1, TimeUnit.SECONDS)){};
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
