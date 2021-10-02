package com.nuitblanche.triple.service;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    @Transactional
    public CityCreateResponseDto createCity(CityCreateRequestDto requestDto){

        Boolean exists = cityRepository.existsByName(requestDto.getName());

        if(exists){
            throw new IllegalArgumentException("already exists city name : " + requestDto.getName());
        }

        City city = City.builder()
                .name(requestDto.getName())
                .build();

        cityRepository.save(city);

        return new CityCreateResponseDto(city);
    }

}
