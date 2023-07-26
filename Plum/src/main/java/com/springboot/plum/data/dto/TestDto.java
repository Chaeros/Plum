package com.springboot.plum.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
@Builder
public class TestDto {
    private String id;
    private String pw;

    public TestDto(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
