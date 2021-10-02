package com.nuitblanche.triple.service;

import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.city.CityRepository;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistory;
import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistoryRepository;
import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.CityLookUpHistoryCreateRequestDto;
import com.nuitblanche.triple.dto.CityLookUpHistoryCreateResponseDto;
import com.nuitblanche.triple.exception.CCityNotFoundException;
import com.nuitblanche.triple.exception.CUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CityLookUpHistoryService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CityLookUpHistoryRepository cityLookUpHistoryRepository;

    @Transactional
    public CityLookUpHistoryCreateResponseDto createCityLookUpHistory(CityLookUpHistoryCreateRequestDto requestDto){

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CUserNotFoundException("user not found id : " + requestDto.getUserId()));

        City city = cityRepository.findByName(requestDto.getCityName())
                .orElseThrow(() -> new CCityNotFoundException("city not found name : " + requestDto.getCityName()));

        CityLookUpHistory cityLookUpHistory = new CityLookUpHistory();
        cityLookUpHistory.updateUser(user);
        cityLookUpHistory.updateCity(city);

        cityLookUpHistoryRepository.save(cityLookUpHistory);

        return new CityLookUpHistoryCreateResponseDto(cityLookUpHistory);
    }
}
