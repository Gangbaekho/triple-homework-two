package com.nuitblanche.triple.cityrepository;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryFindRegisteredCitiesTest {

    @Autowired
    private CityRepository cityRepository;

    @After
    public void cleanUp(){
        cityRepository.deleteAll();
    }

    @Test
    public void 최근_일주일간_등록된_도시가_있는경우(){

        City cityOne = City.builder()
                .name("seoul")
                .build();

        City cityTwo = City.builder()
                .name("tokyo")
                .build();

        cityRepository.save(cityOne);
        cityRepository.save(cityTwo);

        LocalDate now = LocalDate.now();
        LocalDate weekAgo = now.minusDays(7L);
        List<City> cities = cityRepository.findRegisteredCitiesWithInOneWeek(weekAgo, now);
        assertThat(cities.size()).isEqualTo(2);
    }

    @Test
    public void 최근_일주일간_등록된_도시가_없는경우(){

        City cityOne = City.builder()
                .name("seoul")
                .build();

        City cityTwo = City.builder()
                .name("tokyo")
                .build();

        cityRepository.save(cityOne);
        cityRepository.save(cityTwo);

        LocalDate now = LocalDate.now().plusDays(8L);
        LocalDate weekAgo = now.minusDays(7L);
        List<City> cities = cityRepository.findRegisteredCitiesWithInOneWeek(weekAgo, now);
        assertThat(cities.size()).isEqualTo(0);
    }
}
