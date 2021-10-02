package com.nuitblanche.triple.citycontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.travel.Travel;
import com.nuitblanche.triple.domain.travel.TravelRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import com.nuitblanche.triple.dto.CityGetRequestDto;
import com.nuitblanche.triple.dto.CityGetResponseDto;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerGetCitiesTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setup(){

        User user = User.builder()
                .name("tester")
                .build();

        userRepository.save(user);

        String seoul = "seoul";
        String busan = "busan";
        String soowon = "soowon";
        String hwasung = "hwasung";
        String junjoo = "junjoo";
        String tokyo = "tokyo";
        String newyork = "newyork";
        String beijing = "beijing";
        String proto = "proto";
        String hawai =" hawai";
        String looksam = "looksam";

        City seoulCity = City.builder()
                .name(seoul)
                .build();

        City busanCity = City.builder()
                .name(busan)
                .build();

        City soowonCity = City.builder()
                .name(soowon)
                .build();

        City hwasungCity = City.builder()
                .name(hwasung)
                .build();

        City junjooCity = City.builder()
                .name(junjoo)
                .build();

        City tokyoCity = City.builder()
                .name(tokyo)
                .build();

        City newyorkCity = City.builder()
                .name(newyork)
                .build();

        City beijingCity = City.builder()
                .name(beijing)
                .build();

        City protoCity = City.builder()
                .name(proto)
                .build();

        City hawaiCity = City.builder()
                .name(hawai)
                .build();

        City looksamCity = City.builder()
                .name(looksam)
                .build();

        cityRepository.save(seoulCity);
        cityRepository.save(busanCity);
        cityRepository.save(soowonCity);
        cityRepository.save(hwasungCity);
        cityRepository.save(junjooCity);
        cityRepository.save(tokyoCity);
        cityRepository.save(newyorkCity);
        cityRepository.save(beijingCity);
        cityRepository.save(protoCity);
        cityRepository.save(hawaiCity);
        cityRepository.save(looksamCity);
    }

    @Test
    public void 현재_여행도시_11개인_경우(){

        User user = userRepository.findAll().get(0);
        List<City> allCities = cityRepository.findAll();

        LocalDate now = LocalDate.now();

        // 11개
        for(City city : allCities){
            Travel travel = Travel.builder()
                    .startDate(now)
                    .endDate(now)
                    .build();

            travel.updateUser(user);
            travel.updateCity(city);

            travelRepository.save(travel);
        }

        CityGetRequestDto requestDto = CityGetRequestDto.builder()
                .userId(user.getId())
                .build();

        String postUrl = "http://localhost:" + port + "/cities/list";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityGetRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<List> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,List.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponseEntityOne.getBody().size()).isEqualTo(11);
    }

    @Test
    public void 현재_여행도시_8개_여행계획3개인경우(){

        User user = userRepository.findAll().get(0);
        List<City> allCities = cityRepository.findAll();

        LocalDate now = LocalDate.now();
        LocalDate after8days = now.plusDays(8L);

        // 현재 여행도시 8개
        for(int i = 0 ; i < 8 ; i++){
            Travel travel = Travel.builder()
                    .startDate(now)
                    .endDate(now)
                    .build();

            travel.updateUser(user);
            travel.updateCity(allCities.get(i));

            travelRepository.save(travel);
        }

        // 미래 여행 계획 3개
        for(int i = 8 ; i < 11 ; i++){
            Travel travel = Travel.builder()
                    .startDate(after8days)
                    .endDate(after8days)
                    .build();

            travel.updateUser(user);
            travel.updateCity(allCities.get(i));

            travelRepository.save(travel);
        }

        CityGetRequestDto requestDto = CityGetRequestDto.builder()
                .userId(user.getId())
                .build();

        String postUrl = "http://localhost:" + port + "/cities/list";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityGetRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<List> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,List.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        // list는 10개
        assertThat(postResponseEntityOne.getBody().size()).isEqualTo(10);
    }

    @Test
    public void 현재_여행도시_5개_여행계획3개_최근_등록도시_10개인경우(){

        User user = userRepository.findAll().get(0);
        List<City> allCities = cityRepository.findAll();

        LocalDate now = LocalDate.now();
        LocalDate after8days = now.plusDays(8L);

        // 현재 여행도시 5개
        for(int i = 0 ; i < 5 ; i++){
            Travel travel = Travel.builder()
                    .startDate(now)
                    .endDate(now)
                    .build();

            travel.updateUser(user);
            travel.updateCity(allCities.get(i));

            travelRepository.save(travel);
        }

        // 미래 여행 계획 3개
        for(int i = 5 ; i < 8 ; i++){
            Travel travel = Travel.builder()
                    .startDate(after8days)
                    .endDate(after8days)
                    .build();

            travel.updateUser(user);
            travel.updateCity(allCities.get(i));

            travelRepository.save(travel);
        }

        CityGetRequestDto requestDto = CityGetRequestDto.builder()
                .userId(user.getId())
                .build();

        String postUrl = "http://localhost:" + port + "/cities/list";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityGetRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<List> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,List.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        for(Object o : postResponseEntityOne.getBody()){
            CityGetResponseDto responseDto = mapper.convertValue(o,CityGetResponseDto.class);
            System.out.println(responseDto);
        }

        // list는 10개
        assertThat(postResponseEntityOne.getBody().size()).isEqualTo(10);
    }

    @After
    public void cleanup(){

        travelRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
    }
}
