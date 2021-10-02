package com.nuitblanche.triple.cityrepository;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryFindNoInListTest {

    @Autowired
    private CityRepository cityRepository;

    @After
    public void cleanup(){

        cityRepository.deleteAll();
    }

    @Test
    public void 리스트_제외_시티_조회(){

        for(int i = 0 ; i < 5 ; i++){
            City city = City.builder()
                    .name("city" + i)
                    .build();

            cityRepository.save(city);
        }

        List<Long> cityIds = new ArrayList<>();

        List<City> cities = cityRepository.findAll();

        for(int i = 0 ; i < cities.size() ; i += 2){
            cityIds.add(cities.get(i).getId());
        }


        Pageable pageable = PageRequest.of(0,10);
        List<City> citiesNotInList = cityRepository.findNotInList(cityIds,pageable);

        assertThat(citiesNotInList.size()).isEqualTo(2);
    }

    @Test
    public void 리스트_10개_제한(){

        for(int i = 0 ; i < 30 ; i++){
            City city = City.builder()
                    .name("city" + i)
                    .build();

            cityRepository.save(city);
        }

        List<Long> cityIds = new ArrayList<>();
        List<City> cities = cityRepository.findAll();

        for(int i = 0 ; i < cities.size() / 2 ; i++){
            cityIds.add(cities.get(i).getId());
        }

        Pageable pageable = PageRequest.of(0,10);
        List<City> citiesNotInList = cityRepository.findNotInList(cityIds,pageable);

        assertThat(citiesNotInList.size()).isEqualTo(10);
    }
}
