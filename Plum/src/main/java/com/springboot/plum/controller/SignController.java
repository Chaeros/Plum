package com.springboot.plum.controller;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.SignUpResultDto;
import com.springboot.plum.service.SignService;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 예제 13.28
@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@Slf4j
@RequestMapping("/sign-api")
@RequiredArgsConstructor
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/sign-in", consumes="application/json;")
    public SignInResultDto signIn(@RequestBody HashMap<String, Object> map){
        String id=(String)map.get("userid");
        String password=(String)map.get("password");
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        SignInResultDto signInResultDto = signService.signIn(id, password);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up", consumes="application/json;")
    public SignUpResultDto signUp(@RequestBody HashMap<String, Object> map) {
        String id=(String)map.get("id");
        String password=(String)map.get("password");
        String name=(String)map.get("name");
        String phoneNumber=(String)map.get("phoneNumber");
        String role="ROLE_USER";
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", id,
                name, role);
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role, phoneNumber);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", id);
        return signUpResultDto;
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        log.info("[logout] controller");
        signService.logout();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/whoareyou")
    public String whoAreYou(@RequestHeader(value = "Authorization") String token) {
        log.info("[logout] controller");
        String userName = jwtTokenProvider.getUsername(token);
        return userName;
    }

    @GetMapping(value = "/exception")
    public String exceptionTest() throws RuntimeException {
        //throw new RuntimeException("접근이 금지되었습니다.");
        return "error";
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}