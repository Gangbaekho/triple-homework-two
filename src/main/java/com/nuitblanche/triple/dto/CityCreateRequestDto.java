package com.nuitblanche.triple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityCreateRequestDto {

    private String name;

    @Builder
    public CityCreateRequestDto(String name) {
        this.name = name;
    }
}
