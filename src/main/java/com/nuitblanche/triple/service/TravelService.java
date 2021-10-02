package com.nuitblanche.triple.service;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.travel.Travel;
import com.nuitblanche.triple.domain.travel.TravelRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.TravelCreateRequestDto;
import com.nuitblanche.triple.dto.TravelCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public TravelCreateResponseDto createTravel(TravelCreateRequestDto requestDto){

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found id : " + requestDto.getUserId()));

        City city = cityRepository.findById(requestDto.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("city not found id : " + requestDto.getCityId()));

        Long travelCount = travelRepository.countByUserIdAndCityId(requestDto.getUserId(), requestDto.getCityId());

        if(travelCount != 0) {
            throw new IllegalArgumentException("already travel exists. user id : " +  requestDto.getUserId()
                    + " city id :" + requestDto.getCityId());
        }

        Travel travel = Travel.builder()
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();

        travel.updateUser(user);
        travel.updateCity(city);

        travelRepository.save(travel);

        return new TravelCreateResponseDto(travel);
    }
}
