package com.springboot.plum.service.impl;

import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.repository.PostCommentRepository;
import com.springboot.plum.service.BoardPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCommentService {

    private final BoardPostRepository boardPostRepository;
    private final PostCommentRepository postCommentRepository;
    private final BoardPostService boardPostService;

    public void createPostComment(@RequestBody CommentRequestDto commentRequestDto) throws IOException {

        Long postId= commentRequestDto.getPost_id();
        String content = commentRequestDto.getContent();

        // 데이터 수신 확인용
        System.out.println("postId="+commentRequestDto.getPost_id());
        System.out.println("content="+commentRequestDto.getContent());

        // comment 객체 생성시 필요한 데이터
        BoardPost boardPost = boardPostService.findOne(postId);
        String writer = boardPost.getUser().getName();

        // 입력한 데이터를 기반으로 comment 객체 생성
        Comment comment = storeComment(writer,
                content, LocalDateTime.now(),boardPost);

        // postId에 해당되는 게시물에 comment 추가
        BoardPost post = boardPostRepository.findOne(postId);
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);

        postCommentRepository.save(comment);
    }

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
