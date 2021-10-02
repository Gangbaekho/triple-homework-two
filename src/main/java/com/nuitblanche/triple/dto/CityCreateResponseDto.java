package com.nuitblanche.triple.dto;

import com.nuitblanche.triple.domain.city.City;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityCreateResponseDto {

    private Long id;
    private String name;

    public CityCreateResponseDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }
}
