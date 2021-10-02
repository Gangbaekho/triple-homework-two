package com.nuitblanche.triple.controller;

import com.nuitblanche.triple.dto.CityLookUpHistoryCreateRequestDto;
import com.nuitblanche.triple.dto.CityLookUpHistoryCreateResponseDto;
import com.nuitblanche.triple.service.CityLookUpHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/citylookuphistories")
@RestController
public class CityLookUpHistoryControler {

    private final CityLookUpHistoryService cityLookUpHistoryService;

    @PostMapping("")
    public CityLookUpHistoryCreateResponseDto createCityLookUpHistory
            (@RequestBody CityLookUpHistoryCreateRequestDto requestDto){

        return cityLookUpHistoryService.createCityLookUpHistory(requestDto);
    }
}
