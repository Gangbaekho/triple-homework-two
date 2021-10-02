package com.nuitblanche.triple.controller;

import com.nuitblanche.triple.dto.CityCreateRequestDto;
import com.nuitblanche.triple.dto.CityCreateResponseDto;
import com.nuitblanche.triple.dto.CityGetRequestDto;
import com.nuitblanche.triple.dto.CityGetResponseDto;
import com.nuitblanche.triple.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/cities")
@RestController
public class CityController {

    private final CityService cityService;

    @PostMapping("")
    public CityCreateResponseDto createCity(@RequestBody CityCreateRequestDto requestDto){

        return cityService.createCity(requestDto);
    }

    @PostMapping("/list")
    public List<CityGetResponseDto> getCities(@RequestBody CityGetRequestDto requestDto){

        return cityService.getCities(requestDto);
    }
}
