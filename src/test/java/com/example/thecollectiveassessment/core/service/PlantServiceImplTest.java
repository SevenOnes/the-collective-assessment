package com.example.thecollectiveassessment.core.service;

import com.example.thecollectiveassessment.core.dtos.GetGenerationPercPlantByLocationResponse;
import com.example.thecollectiveassessment.core.dtos.GetPaginatedResponse;
import com.example.thecollectiveassessment.core.dtos.GetPlantByLocationDto;
import com.example.thecollectiveassessment.core.enums.OrderBy;
import com.example.thecollectiveassessment.core.model.Plant;
import com.example.thecollectiveassessment.core.repository.PlantRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utils.TestUtils;

import javax.management.InvalidAttributeValueException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlantServiceImplTest {

    @Mock
    PlantRepository repository;

    @InjectMocks
    PlantServiceImpl service;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetTotalGenerationPowerWithCorrectLocation() {
        when(repository.getTotalPowerGenerationByLocation(any())).thenReturn(null);
        Long l = service.getTotalGenerationPower("sas");
        assertEquals(l.longValue(), 0L);

        when(repository.getTotalPowerGenerationByLocation(any())).thenReturn(15L);
        l = service.getTotalGenerationPower("sas");
        assertEquals(l.longValue(), 15L);
    }

    @Test
    public void testGetPlantById() {
        Plant p = TestUtils.generateRandomPlant(1);
        when(repository.getPlantById(1)).thenReturn(p);
        Plant response = service.getPlantById("1");
        assertEquals(p, response);

        exception.expect(NumberFormatException.class);
        service.getPlantById("1asda");
    }

    @Test
    public void testGetOrderedPlants() throws InvalidAttributeValueException {
        List<Plant> topPlants = Arrays.asList(TestUtils.generateRandomPlant(1), TestUtils.generateRandomPlant(2));
        List<Plant> bottomPlants = Arrays.asList(TestUtils.generateRandomPlant(3), TestUtils.generateRandomPlant(4));
        GetPlantByLocationDto temp = new GetPlantByLocationDto()
                .setLocation("location")
                .setOrderBy(OrderBy.top);
        temp.setLimit(3);
        temp.setOffset(3);


        when(repository.getTopPlants("location", 3)).thenReturn(topPlants);
        when(repository.getBottomPlants("location", 3)).thenReturn(bottomPlants);

        assertEquals(topPlants, service.getOrderedPlants(temp));

        assertEquals(bottomPlants, service.getOrderedPlants(temp.setOrderBy(OrderBy.bottom)));

        exception.expect(InvalidAttributeValueException.class);
        service.getOrderedPlants(
                new GetPlantByLocationDto()
                        .setLocation(UUID.randomUUID().toString())
                        .setOrderBy(null)
        );
    }

    @Test
    public void testGetPlantsByLocation() {
        List<Plant> plants = Arrays.asList(
                TestUtils.generateRandomPlant(1),
                TestUtils.generateRandomPlant(2),
                TestUtils.generateRandomPlant(3),
                TestUtils.generateRandomPlant(4),
                TestUtils.generateRandomPlant(5)
        );
        GetPlantByLocationDto temp = new GetPlantByLocationDto()
                .setLocation("location")
                .setOrderBy(OrderBy.top);
        temp.setLimit(4);
        temp.setOffset(3);
        when(repository.getPlantsByLocation("location", 5, 3)).thenReturn(plants);

        GetPaginatedResponse<Plant> resp1 = service.getPlantsByLocation(temp);

        assertEquals(resp1.getOffset().longValue(), 7);
        assertEquals(resp1.getPlants(), plants.subList(0, plants.size() - 1));
        assertTrue(resp1.isMoreExists());

        temp.setLimit(7);
        when(repository.getPlantsByLocation("location", 8, 3)).thenReturn(plants);
        GetPaginatedResponse<Plant> resp2 = service.getPlantsByLocation(temp);

        assertEquals(resp2.getOffset().longValue(), 8);
        assertEquals(resp2.getPlants(), plants);
        assertFalse(resp2.isMoreExists());

    }

    @Test
    public void testFetPercentageProductionPlantsByLocation() {
        List<Plant> plants = Arrays.asList(
                TestUtils.generateRandomPlant(1),
                TestUtils.generateRandomPlant(2),
                TestUtils.generateRandomPlant(3),
                TestUtils.generateRandomPlant(4),
                TestUtils.generateRandomPlant(5)
        );
        Long totalGeneration = plants
                .stream()
                .mapToLong(Plant::getGenerationAmount)
                .sum();
        when(repository.getTotalActivePlantsByLocation(any())).thenReturn(5L);
        when(repository.getPlantsByLocation(any())).thenReturn(plants);
        when(repository.getTotalPowerGenerationByLocation(any())).thenReturn(totalGeneration);

        GetGenerationPercPlantByLocationResponse response = service.getPercentageProductionPlantsByLocation("location");

        assertEquals(response.getLocation(), "location");
        assertEquals(response.getTotalPlants().longValue(), 5L);
        assertEquals(response.getTotalGeneration(), totalGeneration);
        Map<String, Double> percentages = response.getPercentages();
        for (Plant p : plants){
            String key = p.getId()+"#"+p.getName();
            assertTrue(percentages.containsKey(key));
            assertEquals(percentages.get(key), Math.round(100.0 * 100 * p.getGenerationAmount()/totalGeneration)/100.0, 0.2);
        }
    }
}
