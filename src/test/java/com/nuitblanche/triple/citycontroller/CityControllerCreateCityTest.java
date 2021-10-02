package com.nuitblanche.triple.citycontroller;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerCreateCityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){

        City city = City.builder()
                .name("duplicated")
                .build();

        cityRepository.save(city);
    }

    @Test
    public void 도시_생성_성공_경우(){

        String name = "seoul";

        CityCreateRequestDto requestDto = CityCreateRequestDto.builder()
                .name(name)
                .build();

        String postUrl = "http://localhost:" + port + "/cities";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<CityCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,CityCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<City> cities = cityRepository.findAll();
        assertThat(cities.size()).isEqualTo(2);
    }

    @Test
    public void 도시_이름_중복_경우(){

        String name = "duplicated";

        CityCreateRequestDto requestDto = CityCreateRequestDto.builder()
                .name(name)
                .build();

        String postUrl = "http://localhost:" + port + "/cities";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<CityCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,CityCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<City> cities = cityRepository.findAll();
        assertThat(cities.size()).isEqualTo(1);
    }

    @After
    public void cleanup(){

        cityRepository.deleteAll();
    }
}
