package com.nuitblanche.triple.usercontroller;

import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.UserCreateRequestDto;
import com.nuitblanche.triple.dto.UserCreateResponseDto;
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
public class UserControllerCreateUserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){

        User user = User.builder()
                .name("duplicated")
                .build();

        userRepository.save(user);
    }

    @Test
    public void 유저_등록_성공_경우(){

        String name = "username";

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .name(name)
                .build();

        String postUrl = "http://localhost:" + port + "/users";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<UserCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,UserCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void 중복_아이디_가입_경우(){

        String name = "duplicated";

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .name(name)
                .build();

        String postUrl = "http://localhost:" + port + "/users";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<UserCreateResponseDto> postResponseEntityOne = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,UserCreateResponseDto.class);
        assertThat(postResponseEntityOne.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @After
    public void cleanup(){
        userRepository.deleteAll();
    }
}
