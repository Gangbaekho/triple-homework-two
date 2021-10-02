package com.nuitblanche.triple.service;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.travel.Travel;
import com.nuitblanche.triple.domain.travel.TravelRepository;
import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import com.nuitblanche.triple.dto.CityGetRequestDto;
import com.nuitblanche.triple.dto.CityGetResponseDto;
import com.nuitblanche.triple.exception.CCityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CityService {

    private static final int CITY_AMOUNT = 10;
    private final CityRepository cityRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public CityCreateResponseDto createCity(CityCreateRequestDto requestDto){

        Boolean exists = cityRepository.existsByName(requestDto.getName());

        if(exists){
            throw new CCityAlreadyExistsException("already exists city name : " + requestDto.getName());
        }

        City city = City.builder()
                .name(requestDto.getName())
                .build();

        cityRepository.save(city);

        return new CityCreateResponseDto(city);
    }

    @Transactional(readOnly = true)
    public List<CityGetResponseDto> getCities(CityGetRequestDto requestDto){

        LocalDate now = LocalDate.now();
        List<City> cities = new ArrayList<>();

        List<Travel> travelsNow = travelRepository.findTravelNowByUserIdAndLocalDate(requestDto.getUserId(), now);

        travelsNow.stream()
                .forEach(travel -> {
                    cities.add(travel.getCity());
                });

        if(cities.size() < CITY_AMOUNT){
            List<Travel> travelsFuture = travelRepository.findTravelFutureByUserIdAndLocalDate(requestDto.getUserId(), now);
            travelsFuture.stream()
                    .forEach(travel -> {
                        if(cities.size() < CITY_AMOUNT){
                            cities.add(travel.getCity());
                        }
                    });
        }

        if(cities.size() < CITY_AMOUNT){
            List<City> registeredCitiesWithInOneWeek = cityRepository.findRegisteredCitiesWithInOneWeek(now.minusDays(7), now);
            registeredCitiesWithInOneWeek.stream()
                    .forEach(city -> {
                        if(cities.size () < CITY_AMOUNT){
                            cities.add(city);
                        }
                    });
        }

        if(cities.size() < CITY_AMOUNT){
            List<City> oneSearchedCitiesWithInOneWeek = cityRepository.findByOneLookedUpWithInWeek(requestDto.getUserId(),now.minusDays(7),now);
            oneSearchedCitiesWithInOneWeek.stream()
                    .forEach(city -> {
                        if(cities.size () < CITY_AMOUNT && !cities.contains(city)){
                            cities.add(city);
                        }
                    });
        }

        if(cities.size() < CITY_AMOUNT){

            List<Long> cityIds = cities.stream()
                    .map(city -> city.getId())
                    .collect(Collectors.toList());

            Pageable pageable = PageRequest.of(0,10);
            List<City> citiesNotInList = cityRepository.findNotInList(cityIds,pageable);
            citiesNotInList.stream()
                    .forEach(city -> {
                        if(cities.size () < CITY_AMOUNT){
                            cities.add(city);
                        }
                    });
        }

        List<CityGetResponseDto> responseDtos = cities.stream()
                .map(city -> new CityGetResponseDto(city))
                .collect(Collectors.toList());

        return responseDtos;
    }


}
