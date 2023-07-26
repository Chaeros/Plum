package com.springboot.plum.controller;

import com.springboot.plum.data.dto.TestDto;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins="*")
public class TestController {
    @PostMapping(value = "/login", consumes="application/json;")
    public String signIn(@RequestBody HashMap<String, Object> map){
        System.out.println(map);
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }

    @GetMapping(value = "/hello2", consumes="application/json;")
    public String hello(){
        System.out.println("hi");
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }
}
