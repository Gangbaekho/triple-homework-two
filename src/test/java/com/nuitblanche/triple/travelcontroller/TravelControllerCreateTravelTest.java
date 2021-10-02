package com.nuitblanche.triple.travelcontroller;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.travel.Travel;
import com.nuitblanche.triple.domain.travel.TravelRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import com.nuitblanche.triple.dto.TravelCreateRequestDto;
import com.nuitblanche.triple.dto.TravelCreateResponseDto;
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
public class TravelControllerCreateTravelTest {

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

        City city = City.builder()
                .name("duplicated")
                .build();

        cityRepository.save(city);

        City seoulCity = City.builder()
                .name("seoul")
                .build();

        cityRepository.save(seoulCity);

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1L);
        LocalDate endDate = now.plusDays(2L);

        Travel travel = Travel.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        travel.updateUser(user);
        travel.updateCity(city);

        travelRepository.save(travel);
    }

    @Test
    public void 여행_등록_성공_경우(){

        User user = userRepository.findByName("tester")
                .orElseThrow(() -> new IllegalArgumentException("not found user name : " + "tester"));

        City city = cityRepository.findByName("seoul")
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + "seoul"));

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1L);
        LocalDate endDate = now.plusDays(2L);

        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .userId(user.getId())
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        String postUrl = "http://localhost:" + port + "/travels";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TravelCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TravelCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TravelCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Travel> travels = travelRepository.findAll();
        assertThat(travels.size()).isEqualTo(2);
    }

    @Test
    public void 중복된_여행지를_등록하는_경우(){

        User user = userRepository.findByName("tester")
                .orElseThrow(() -> new IllegalArgumentException("not found user name : " + "tester"));

        // duplicated city.
        City city = cityRepository.findByName("duplicated")
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + "seoul"));

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1L);
        LocalDate endDate = now.plusDays(2L);

        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .userId(user.getId())
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        String postUrl = "http://localhost:" + port + "/travels";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TravelCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TravelCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TravelCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<Travel> travels = travelRepository.findAll();
        assertThat(travels.size()).isEqualTo(1);
    }

    @Test
    public void 시작날짜가_오늘보다_빠른경우(){

        User user = userRepository.findByName("tester")
                .orElseThrow(() -> new IllegalArgumentException("not found user name : " + "tester"));

        City city = cityRepository.findByName("seoul")
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + "seoul"));

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(1L);
        LocalDate endDate = now.plusDays(2L);

        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .userId(user.getId())
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        String postUrl = "http://localhost:" + port + "/travels";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TravelCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TravelCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TravelCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<Travel> travels = travelRepository.findAll();
        assertThat(travels.size()).isEqualTo(1);
    }

    @Test
    public void 종료날짜가_오늘보다_빠른경우(){

        User user = userRepository.findByName("tester")
                .orElseThrow(() -> new IllegalArgumentException("not found user name : " + "tester"));

        City city = cityRepository.findByName("seoul")
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + "seoul"));

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1L);
        LocalDate endDate = now.minusDays(2L);

        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .userId(user.getId())
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        String postUrl = "http://localhost:" + port + "/travels";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TravelCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TravelCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TravelCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<Travel> travels = travelRepository.findAll();
        assertThat(travels.size()).isEqualTo(1);
    }

    @Test
    public void 시작날짜가_종료날짜보다_빠른경우(){

        User user = userRepository.findByName("tester")
                .orElseThrow(() -> new IllegalArgumentException("not found user name : " + "tester"));

        City city = cityRepository.findByName("seoul")
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + "seoul"));

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(3L);
        LocalDate endDate = now.plusDays(1L);

        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .userId(user.getId())
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();

        String postUrl = "http://localhost:" + port + "/travels";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TravelCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TravelCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TravelCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<Travel> travels = travelRepository.findAll();
        assertThat(travels.size()).isEqualTo(1);
    }

    @After
    public void cleanup(){

        travelRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
    }

}
