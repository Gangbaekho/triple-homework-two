package com.nuitblanche.triple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    private String name;

    @Builder
    public UserCreateRequestDto(String name) {
        this.name = name;
    }
}
