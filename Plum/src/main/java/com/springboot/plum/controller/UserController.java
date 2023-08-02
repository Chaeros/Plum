package com.springboot.plum.controller;


import com.springboot.plum.data.dto.SignInResultDto;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/announce", consumes="application/json;")
    public HashMap<String, Object> signIn(@RequestBody HashMap<String, Object> map){
        String data1=(String)map.get("data1");
        String data2=(String)map.get("data2");
        System.out.println(data1 + " " + data2);
        return map;
    }
}
