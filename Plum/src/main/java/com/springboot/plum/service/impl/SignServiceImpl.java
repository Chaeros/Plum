package com.springboot.plum.service.impl;

import com.springboot.plum.common.CommonResponse;
import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.SignUpResultDto;
import com.springboot.plum.data.entity.User;
import com.springboot.plum.repository.UserRepository;
import com.springboot.plum.service.SignService;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 예제 13.25
@Service
@Slf4j
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String role) {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByUid(id);
        LOGGER.info("[getSignInResult] Id : {}", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                        user.getRoles()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    // 로그아웃
    @Transactional
    public void logout() {
        //Token에서 로그인한 사용자 정보 get해 로그아웃 처리
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[logout] Uid = {}",user.getUid());
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + user.getUid()) != null) {
            redisTemplate.delete("JWT_TOKEN:" + user.getUid()); //Token 삭제
        }
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}