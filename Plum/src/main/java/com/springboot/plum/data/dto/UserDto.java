package com.springboot.plum.data.dto;

import com.springboot.plum.data.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private long id;
    private String uid; // 회원 ID (JWT 토큰 내 정보)
    private String name;
    private String phoneNumber;

    public UserDto(User user){
        this.id=user.getId();
        this.uid=user.getUid();
        this.name=user.getName();
        this.phoneNumber=user.getPhoneNumber();
    }

    public UserDto(long id, String uid, String name, String phoneNumber) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
