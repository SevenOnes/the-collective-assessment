package com.example.thecollectiveassessment.core.repository;

import com.example.thecollectiveassessment.core.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {

    @Query(value = "SELECT sum(p.generation_amount) from plant p where p.location = ?1 and p.is_active = 1", nativeQuery = true)
    Long getTotalPowerGenerationByLocation(String location);

    @Query(value = "SELECT count(*) from plant p where p.location = ?1 and p.is_active = 1", nativeQuery = true)
    Long getTotalActivePlantsByLocation(String location);

    @Query(value = "SELECT * from plant p where id = ?1", nativeQuery = true)
    Plant getPlantById(int id);

    @Query(value = "SELECT * from plant p where location = ?1 and is_active = 1 order by generation_amount desc limit ?2", nativeQuery = true)
    List<Plant> getTopPlants(String location, int limit);

    @Query(value = "SELECT * from plant p where location = ?1 and is_active = 1 order by generation_amount asc limit ?2", nativeQuery = true)
    List<Plant> getBottomPlants(String location, int limit);

    @Query(value = "SELECT * from plant p where location = ?1 and is_active = 1 limit ?2 offset ?3", nativeQuery = true)
    List<Plant> getPlantsByLocation(String location, int limit, int offset);

    @Query(value = "SELECT * from plant p where location = ?1 and is_active = 1", nativeQuery = true)
    List<Plant> getPlantsByLocation(String location);
}
