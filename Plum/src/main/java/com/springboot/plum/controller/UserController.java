package com.springboot.plum.controller;


import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.TestDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/announce", consumes="application/json;")
    public HashMap<String, Object> signIn(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        System.out.println();
        String data1=(String)map.get("data1");
        String data2=(String)map.get("data2");
        System.out.println(data1 + " " + data2);
        return map;
    }

    @GetMapping(value = "/hello3")
    public String hello3(){
        System.out.println("hi");
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }
}
