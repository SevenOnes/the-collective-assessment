package com.example.thecollectiveassessment.core.miscellaneous;

import com.example.thecollectiveassessment.core.model.Plant;
import com.example.thecollectiveassessment.core.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class SampleDataLoader {

    private static PlantRepository repository;

    @Autowired
    public SampleDataLoader(PlantRepository repository) {
        SampleDataLoader.repository = repository;
    }

    public static void readFromFileAndSaveToDB(){
        List<Plant> plants = new ArrayList<>();
        try {
            File myObj = new File("src/main/resources/sample-data.tsv");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("\t");
                plants.add(createPlantObject(data));
                i++;
                if(plants.size() >= 1000){
                    repository.saveAll(plants);
                    plants.clear();
                }
            }
            myReader.close();
            if(plants.size() > 0) {
                repository.saveAll(plants);
            }
            System.out.println("Data uploaded: " + i);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static Plant createPlantObject(String[] data){
        boolean isActive = data.length >= 7; //&& !data[6].equals("0");
        Long amount = null;
        if(isActive){
            String tempString = data[6].replace(",", "");
            if(tempString.startsWith("(")){
                amount = Long.parseLong(tempString.substring(1, tempString.length()-1)) * -1;
            } else {
                amount = Long.parseLong(tempString);
            }
        }
        return new Plant(
                Integer.parseInt(data[0].trim()),
                Integer.parseInt(data[1].trim()),
                data[2].trim(),
                data[3].trim(),
                data[4].trim(),
                data[5].trim(),
                amount,
                isActive
        );
    }
}
