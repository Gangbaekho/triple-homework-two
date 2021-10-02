package com.nuitblanche.triple.citylookuphistoryrepository;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistory;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistoryRepository;
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
public class CityLookUpHistoryRepositoryFindOneLookedUpTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityLookUpHistoryRepository cityLookUpHistoryRepository;

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
    }

    @Test
    public void 최근_일주일_단건조회된_도시들_하나인경우(){

        String tokyo = "tokyo";
        String seoul = "seoul";

        User user = userRepository.findAll().get(0);

        City tokyoCity = cityRepository.findByName(tokyo)
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + tokyo));

        City seoulCity = cityRepository.findByName(seoul)
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + seoul));

        CityLookUpHistory historyOne =  new CityLookUpHistory();
        historyOne.updateUser(user);
        historyOne.updateCity(tokyoCity);

        CityLookUpHistory historyTwo =  new CityLookUpHistory();
        historyOne.updateUser(user);
        historyOne.updateCity(tokyoCity);

        cityLookUpHistoryRepository.save(historyOne);
        cityLookUpHistoryRepository.save(historyTwo);

        CityLookUpHistory historyThree =  new CityLookUpHistory();
        historyOne.updateUser(user);
        historyOne.updateCity(seoulCity);

        cityLookUpHistoryRepository.save(historyThree);

        LocalDate now = LocalDate.now();
        LocalDate weekAgo = now.minusDays(7L);
        List<CityLookUpHistory> cityLookUpHistories = cityLookUpHistoryRepository.findByOneLookedUpWithInWeek(user.getId(),weekAgo,now);
        assertThat(cityLookUpHistories.size()).isEqualTo(1);

    }

    @Test
    public void 최근_일주일_단건조회된_도시들_두개인경우(){

        String tokyo = "tokyo";
        String seoul = "seoul";

        User user = userRepository.findAll().get(0);

        City tokyoCity = cityRepository.findByName(tokyo)
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + tokyo));

        City seoulCity = cityRepository.findByName(seoul)
                .orElseThrow(() -> new IllegalArgumentException("not found city name : " + seoul));

        CityLookUpHistory historyOne =  new CityLookUpHistory();
        historyOne.updateUser(user);
        historyOne.updateCity(tokyoCity);

        cityLookUpHistoryRepository.save(historyOne);

        CityLookUpHistory historyTwo =  new CityLookUpHistory();
        historyOne.updateUser(user);
        historyOne.updateCity(seoulCity);

        cityLookUpHistoryRepository.save(historyTwo);

        LocalDate now = LocalDate.now();
        LocalDate weekAgo = now.minusDays(7L);
        List<CityLookUpHistory> cityLookUpHistories = cityLookUpHistoryRepository.findByOneLookedUpWithInWeek(user.getId(),weekAgo,now);
        assertThat(cityLookUpHistories.size()).isEqualTo(2);
        assertThat(cityLookUpHistories.get(0).getCity().getName()).isEqualTo(seoul);
    }

    @After
    public void cleanup(){

        cityLookUpHistoryRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
    }
}
