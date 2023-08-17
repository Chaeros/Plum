package com.springboot.plum.data.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long post_id;
    private String content;

}
