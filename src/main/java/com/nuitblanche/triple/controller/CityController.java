package com.nuitblanche.triple.controller;

import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import com.nuitblanche.triple.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/cities")
@RestController
public class CityController {

    private final CityService cityService;

    @PostMapping("")
    public CityCreateResponseDto createCity(@RequestBody CityCreateRequestDto requestDto){

        return cityService.createCity(requestDto);
    }
}
