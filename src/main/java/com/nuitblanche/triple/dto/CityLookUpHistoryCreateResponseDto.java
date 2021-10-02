package com.nuitblanche.triple.dto;

import com.nuitblanche.triple.domain.citylookuphistory.CityLookUpHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityLookUpHistoryCreateResponseDto {

    private Long id;
    private Long cityId;
    private String cityName;

    public CityLookUpHistoryCreateResponseDto(CityLookUpHistory cityLookUpHistory){
        this.id = cityLookUpHistory.getId();
        this.cityId = cityLookUpHistory.getCity().getId();
        this.cityName = cityLookUpHistory.getCity().getName();
    }


}
