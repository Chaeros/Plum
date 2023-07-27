package com.springboot.plum.controller;

import com.springboot.plum.data.dto.TestDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class TestController {
    @PostMapping(value = "/login", consumes="application/json;")
    public TestDto signIn(@RequestBody List<HashMap<String, Object>> map){
        System.out.println(map);
//        //System.out.println(map.get("aaa"));
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(map);
        for(int i=0;i<map.size();++i){
            System.out.println(map.get(i));
            JSONObject jsonObj = new JSONObject(map.get(i));
            System.out.println("email="+(String)jsonObj.get("email"));
        }
        TestDto testDto =new TestDto("park","1234");
        return testDto;
    }

    @GetMapping(value = "/hello2")
    public String hello(){
        System.out.println("hi");
        TestDto testDto =new TestDto("park","1234");
        return "hi";
    }
}
