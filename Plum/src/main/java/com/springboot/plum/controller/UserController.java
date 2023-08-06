package com.springboot.plum.controller;


import com.springboot.plum.data.dto.NoticeBoardDto;
import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.TestDto;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

//@RequiredArgsConstructor는 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다.
//Lombok으로 스프링에서 DI(의존성 주입)의 방법 중에 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션이다.
//새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final NoticeBoardService noticeBoardService;

    @PostMapping(value = "/announce", consumes="application/json;")
    public List<NoticeBoard> signIn(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        List<NoticeBoard> members = noticeBoardService.findMembers();
        //NoticeBoardDto boardDto = new NoticeBoardDto(members)
        return members;
    }

    @PostMapping(value = "/hello4")
    public String hello4(){
        System.out.println("hi");
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }

    @GetMapping(value = "/hello3")
    public String hello3(){
        System.out.println("hi");
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }
}
