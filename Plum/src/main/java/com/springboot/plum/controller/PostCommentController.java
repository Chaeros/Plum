package com.springboot.plum.controller;

import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.service.impl.PostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class PostCommentController {

    private final PostCommentService postCommentService;

    // 특정 게시글에 달리는 댓글 생성
    @PostMapping()
    public void createPostComment(@RequestBody CommentRequestDto commentRequestDto,
                                  @RequestHeader(value = "Authorization") String token) throws IOException {
        log.info("[Controller]create postComment");
        postCommentService.createPostComment(token,commentRequestDto);
    }

    // 특정 id를 가진 댓글 하나 불러오기(읽기)
    @GetMapping("/{comment_id}")
    public Comment readPostComment(@PathVariable Long comment_id){
        log.info("[Controller]read postComent");
        return postCommentService.readPostCommnet(comment_id);
    }

    // 특정 id를 가진 댓글 내용 수정
    @PutMapping("/{comment_id}")
    public void updatePostComment(@PathVariable Long comment_id,
                                  @RequestParam String content,
                                  @RequestHeader(value = "Authorization") String token){
        log.info("[Controller]update postComment");
        postCommentService.updatePostComment(token,comment_id,content);
    }

    // 특정 id를 가진 댓글 삭제
    @DeleteMapping("/{comment_id}")
    public void deletePostComment(@PathVariable Long comment_id,
                                  @RequestHeader(value = "Authorization") String token){
        log.info("[Controller]delete postComment");
        postCommentService.deletePostComment(token,comment_id);
    }

    // 특정 게시판의 모든 댓글 목록 불러오기(읽기)
    @GetMapping("/list/post/{post_id}")
    public List<Comment> readePostCommentListByPostId(@PathVariable Long post_id){
        log.info("[Controller]read postCommentList");
        return postCommentService.readPostCommentListByPostId(post_id);
    }

    // 특정 사용자의 모든 댓글 목록 불러오기(읽기)
    @GetMapping("/list/user/{user_id}")
    public List<Comment> readePostCommentListByUserId(@PathVariable Long user_id){
        log.info("[Controller]read postCommentList");
        return postCommentService.readPostCommentListByUserId(user_id);
    }


}