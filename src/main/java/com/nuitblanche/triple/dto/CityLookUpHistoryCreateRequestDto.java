package com.nuitblanche.triple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityLookUpHistoryCreateRequestDto {

    private Long userId;
    private String cityName;

    @Builder
    public CityLookUpHistoryCreateRequestDto(Long userId, String cityName) {
        this.userId = userId;
        this.cityName = cityName;
    }
}
