package com.springboot.plum.controller;

import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.UpdateBoardPostDTO;
import com.springboot.plum.data.dto.UserDto;
import com.springboot.plum.repository.NormalUserRepository;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.impl.NormalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final NormalUserService normalUserService;

    // 사용자 정보 읽기(불러오기)
    @GetMapping()
    public UserDto readUser(@RequestHeader(value = "Authorization") String token){
        log.info("[post read] readUser");
        return normalUserService.readUser(token);
    }

    // 사용자 정보 수정
//    @PutMapping("/{user_uid}")
//    public void updadtePost(@RequestBody UpdateBoardPostDTO updateBoardPostDTO,
//                            @PathVariable long user_uid){
//        log.info("[post update] post_id={}",user_uid);
//        boardPostService.updatePost(updateBoardPostDTO,user_uid);
//    }

}
