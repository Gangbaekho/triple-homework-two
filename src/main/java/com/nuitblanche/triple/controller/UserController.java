package com.nuitblanche.triple.controller;

import com.nuitblanche.triple.dto.UserCreateRequestDto;
import com.nuitblanche.triple.dto.UserCreateResponseDto;
import com.nuitblanche.triple.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public UserCreateResponseDto createUser(@RequestBody UserCreateRequestDto requestDto){

        return userService.createUser(requestDto);
    }
}
