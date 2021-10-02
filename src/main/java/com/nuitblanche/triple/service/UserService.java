package com.nuitblanche.triple.service;

import com.nuitblanche.triple.domain.user.User;
import com.nuitblanche.triple.domain.user.UserRepository;
import com.nuitblanche.triple.dto.UserCreateRequestDto;
import com.nuitblanche.triple.dto.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto requestDto){

        Boolean exists = userRepository.existsByName(requestDto.getName());

        if(exists){
            throw new IllegalArgumentException("already exists user name : " + requestDto.getName());
        }

        User user = User.builder()
                .name(requestDto.getName())
                .build();

        userRepository.save(user);

        return new UserCreateResponseDto(user);
    }
}
