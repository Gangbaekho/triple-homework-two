package com.nuitblanche.triple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TravelCreateRequestDto {

    private Long userId;
    private Long cityId;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public TravelCreateRequestDto(Long userId, Long cityId, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.cityId = cityId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
