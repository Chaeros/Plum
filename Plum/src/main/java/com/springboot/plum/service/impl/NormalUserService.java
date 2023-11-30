package com.springboot.plum.service.impl;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.UpdateBoardPostDTO;
import com.springboot.plum.data.dto.UserDto;
import com.springboot.plum.data.entity.User;
import com.springboot.plum.repository.NormalUserRepository;
import com.springboot.plum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NormalUserService {
    private final NormalUserRepository normalUserRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDto readUser(String token){
        String uid = jwtTokenProvider.getUsername(token);
        User user = normalUserRepository.findOneByID(uid);
        return new UserDto(user);
    }

    public void updateUser(UpdateBoardPostDTO updateBoardPostDTO, long post_id){

    }

}
