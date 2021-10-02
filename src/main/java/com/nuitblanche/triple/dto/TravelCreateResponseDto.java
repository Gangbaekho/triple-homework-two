package com.nuitblanche.triple.dto;

import com.nuitblanche.triple.domain.travel.Travel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TravelCreateResponseDto {

    private Long id;
    private Long userId;
    private Long cityId;
    private LocalDate startDate;
    private LocalDate endDate;

    public TravelCreateResponseDto(Travel travel) {
        this.id = travel.getId();
        this.userId = travel.getUser().getId();
        this.cityId = travel.getCity().getId();
        this.startDate = travel.getStartDate();
        this.endDate = travel.getEndDate();
    }
}
