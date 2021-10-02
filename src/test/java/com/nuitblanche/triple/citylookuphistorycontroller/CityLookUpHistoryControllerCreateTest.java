package com.nuitblanche.triple.citylookuphistorycontroller;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistory;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistoryRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.CityLookUpHistoryCreateRequestDto;
import com.nuitblanche.triple.dto.CityLookUpHistoryCreateResponseDto;
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
public class CityLookUpHistoryControllerCreateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityLookUpHistoryRepository cityLookUpHistoryRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){

        User user = User.builder()
                .name("tester")
                .build();

        userRepository.save(user);

        City city = City.builder()
                .name("seoul")
                .build();

        cityRepository.save(city);
    }

    @Test
    public void 도시_조회_성공_경우(){

        User user = userRepository.findAll().get(0);

        CityLookUpHistoryCreateRequestDto  requestDto = CityLookUpHistoryCreateRequestDto.builder()
                .userId(user.getId())
                .cityName("seoul")
                .build();

        String postUrl = "http://localhost:" + port + "/citylookuphistories";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityLookUpHistoryCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<CityLookUpHistoryCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,CityLookUpHistoryCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<CityLookUpHistory> cityLookUpHistories = cityLookUpHistoryRepository.findAll();
        assertThat(cityLookUpHistories.size()).isEqualTo(1);
    }

    @Test
    public void 도시_조회_실패_유저없는_경우(){

        Long invalidUserId = 10000L;

        CityLookUpHistoryCreateRequestDto  requestDto = CityLookUpHistoryCreateRequestDto.builder()
                .userId(invalidUserId)
                .cityName("seoul")
                .build();

        String postUrl = "http://localhost:" + port + "/citylookuphistories";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityLookUpHistoryCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<CityLookUpHistoryCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,CityLookUpHistoryCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<CityLookUpHistory> cityLookUpHistories = cityLookUpHistoryRepository.findAll();
        assertThat(cityLookUpHistories.size()).isEqualTo(0);
    }

    @Test
    public void 도시_조회_실패_도시없는_경우(){

        String invalidCityName = "invalid-city-name";

        User user = userRepository.findAll().get(0);

        CityLookUpHistoryCreateRequestDto  requestDto = CityLookUpHistoryCreateRequestDto.builder()
                .userId(user.getId())
                .cityName(invalidCityName)
                .build();

        String postUrl = "http://localhost:" + port + "/citylookuphistories";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CityLookUpHistoryCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<CityLookUpHistoryCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,CityLookUpHistoryCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<CityLookUpHistory> cityLookUpHistories = cityLookUpHistoryRepository.findAll();
        assertThat(cityLookUpHistories.size()).isEqualTo(0);
    }

    @After
    public void cleanup(){

        cityLookUpHistoryRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
    }
}
