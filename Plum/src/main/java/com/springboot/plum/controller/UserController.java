package com.springboot.plum.controller;


import com.springboot.plum.data.dto.NoticeBoardDto;
import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.TestDto;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.service.NoticeBoardService;
import com.springboot.plum.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor는 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다.
//Lombok으로 스프링에서 DI(의존성 주입)의 방법 중에 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션이다.
//새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final NoticeBoardService noticeBoardService;
    private final SignService signService;

    @PostMapping(value = "/announce", consumes="application/json;")
    public List<NoticeBoardDto> signIn(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        List<NoticeBoard> noticeBoards = noticeBoardService.findMembers();
        List<NoticeBoardDto> boardDtos = new ArrayList<>();
        NoticeBoardDto boardDto1 = new NoticeBoardDto("자유게시판1");
//        NoticeBoardDto boardDto2 = new NoticeBoardDto("자유게시판2");
        boardDtos.add(boardDto1);
//        boardDtos.add(boardDto2);
        return boardDtos;
    }

}
