package com.springboot.plum.data.dto;

import com.springboot.plum.service.NoticeBoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor // 매개변수 생성자 자동 생성해줌
@ToString
public class NoticeBoardDto {
    private String boardName;
}
