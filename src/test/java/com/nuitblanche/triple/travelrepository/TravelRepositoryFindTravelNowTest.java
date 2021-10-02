package com.nuitblanche.triple.travelrepository;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.travel.Travel;
import com.nuitblanche.triple.domain.travel.TravelRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
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
public class TravelRepositoryFindTravelNowTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TravelRepository travelRepository;

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

        City cityTwo = City.builder()
                .name("tokyo")
                .build();

        cityRepository.save(cityTwo);

        City cityThree = City.builder()
                .name("busan")
                .build();

        cityRepository.save(cityThree);
    }

    @Test
    public void 현재_여행중인_여행_조회(){

        String seoul = "seoul";
        String tokyo = "tokyo";
        String busan = "busan";

        LocalDate now = LocalDate.now();
        LocalDate after8Days = now.plusDays(8L);

        User user = userRepository.findAll().get(0);
        City seoulCity = cityRepository.findByName(seoul)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + seoul));
        City busanCity = cityRepository.findByName(busan)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + busan));
        City tokyoCity = cityRepository.findByName(tokyo)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + tokyo));

        Travel travelOne = Travel.builder()
                .startDate(now)
                .endDate(now)
                .build();

        travelOne.updateUser(user);
        travelOne.updateCity(seoulCity);

        travelRepository.save(travelOne);

        Travel travelTwo = Travel.builder()
                .startDate(now)
                .endDate(now)
                .build();

        travelTwo.updateUser(user);
        travelTwo.updateCity(busanCity);

        travelRepository.save(travelTwo);

        Travel travelThree = Travel.builder()
                .startDate(after8Days)
                .endDate(after8Days)
                .build();

        travelThree.updateUser(user);
        travelThree.updateCity(tokyoCity);

        travelRepository.save(travelThree);

        List<Travel> travels = travelRepository.findTravelNowByUserIdAndLocalDate(user.getId(),now);
        assertThat(travels.size()).isEqualTo(2);
    }

    @Test
    public void 현재_여행중인_여행_조회_정렬(){

        String seoul = "seoul";
        String tokyo = "tokyo";
        String busan = "busan";

        LocalDate now = LocalDate.now();
        LocalDate after8Days = now.plusDays(8L);

        User user = userRepository.findAll().get(0);
        City seoulCity = cityRepository.findByName(seoul)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + seoul));
        City busanCity = cityRepository.findByName(busan)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + busan));
        City tokyoCity = cityRepository.findByName(tokyo)
                .orElseThrow(()-> new IllegalArgumentException("city not found name :" + tokyo));

        Travel travelOne = Travel.builder()
                .startDate(now)
                .endDate(now)
                .build();

        travelOne.updateUser(user);
        travelOne.updateCity(seoulCity);

        travelRepository.save(travelOne);

        Travel travelTwo = Travel.builder()
                .startDate(now.minusDays(1L))
                .endDate(now)
                .build();

        travelTwo.updateUser(user);
        travelTwo.updateCity(busanCity);

        travelRepository.save(travelTwo);

        Travel travelThree = Travel.builder()
                .startDate(after8Days)
                .endDate(after8Days)
                .build();

        travelThree.updateUser(user);
        travelThree.updateCity(tokyoCity);

        travelRepository.save(travelThree);

        List<Travel> travels = travelRepository.findTravelNowByUserIdAndLocalDate(user.getId(),now);
        assertThat(travels.size()).isEqualTo(2);
        assertThat(travels.get(0).getCity().getName()).isEqualTo(busan);
    }

    @After
    public void cleanup(){

        travelRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
    }
}
