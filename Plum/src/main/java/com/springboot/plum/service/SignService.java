package com.springboot.plum.service;

import com.springboot.plum.data.dto.SignInResultDto;
import com.springboot.plum.data.dto.SignUpResultDto;

// 예제 13.24
public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String role, String phoneNumber);

    SignInResultDto signIn(String id, String password) throws RuntimeException;

    void logout();
}