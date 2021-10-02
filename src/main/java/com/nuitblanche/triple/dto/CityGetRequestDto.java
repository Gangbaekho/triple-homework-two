package com.nuitblanche.triple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityGetRequestDto {

    private Long userId;

    @Builder
    public CityGetRequestDto(Long userId) {
        this.userId = userId;
    }
}
