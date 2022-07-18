package utils;

import com.example.thecollectiveassessment.core.model.Plant;

import java.util.UUID;

public class TestUtils {
    public static Plant generateRandomPlant(int x){
        return new Plant()
                .setGenerationAmount(Math.round(Math.random() * 1000))
                .setGeneratorId(UUID.randomUUID().toString())
                .setGeneratorStatus(UUID.randomUUID().toString())
                .setId(x)
                .setLocation(UUID.randomUUID().toString())
                .setName(UUID.randomUUID().toString())
                .setYear(2022)
                .setIsActive(true);
    }
}
