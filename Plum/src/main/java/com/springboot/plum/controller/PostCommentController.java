package com.springboot.plum.controller;

import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.impl.PostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class PostCommentController {

    private final PostCommentService postCommentService;

    // 특정 게시글에 달리는 댓글 생성
    @PostMapping()
    public void createPostComment(@RequestBody CommentRequestDto commentRequestDto) throws IOException {
        log.info("[Service]create postComment");
        postCommentService.createPostComment(commentRequestDto);
    }
}
