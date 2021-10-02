package com.nuitblanche.triple.dto;

import com.nuitblanche.triple.domain.city.City;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityGetResponseDto {

    private Long id;
    private String name;

    public CityGetResponseDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    @Override
    public String toString() {
        return "CityGetResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
