package com.springboot.plum.service.impl;

import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    public Comment storeComment(String writer, String content,
                LocalDateTime localDateTime, BoardPost boardPost) throws IOException {
        return Comment.builder()
                .writer(writer)
                .content(content)
                .commentDate(localDateTime)
                .likeCount(0)
                .boardPost(boardPost)
                .build();
    }
}
