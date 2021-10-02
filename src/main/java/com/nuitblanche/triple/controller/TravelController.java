package com.nuitblanche.triple.controller;

import com.nuitblanche.triple.dto.TravelCreateRequestDto;
import com.nuitblanche.triple.dto.TravelCreateResponseDto;
import com.nuitblanche.triple.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/travels")
@RestController
public class TravelController {

    private final TravelService travelService;

    @PostMapping("")
    public TravelCreateResponseDto createTravel(@RequestBody TravelCreateRequestDto requestDto){

        return travelService.createTravel(requestDto);
    }

}
