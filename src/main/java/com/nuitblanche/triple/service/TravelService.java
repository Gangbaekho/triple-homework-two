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

import java.time.LocalDate;

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

        boolean isValid = isValidDate(requestDto);
        if(!isValid){
            throw new IllegalArgumentException("not valid start date or end date.");
        }

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

    private boolean isValidDate(TravelCreateRequestDto requestDto){

        LocalDate now = LocalDate.now();
        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();

        boolean isValid = true;

        if(startDate.isBefore(now)){
            isValid = false;
        }

        if(endDate.isBefore(now)){
            isValid = false;
        }

        if(startDate.isAfter(endDate)){
            isValid = false;
        }

        return isValid;
    }

}
