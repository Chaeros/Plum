package com.springboot.plum.service.impl;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.entity.User;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.repository.PostCommentRepository;
import com.springboot.plum.repository.UserRepository;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCommentService implements CommentService {

    private final BoardPostRepository boardPostRepository;
    private final PostCommentRepository postCommentRepository;
    private final BoardPostService boardPostService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    // 특정 댓글 생성
    public void createPostComment(String token, CommentRequestDto commentRequestDto) throws IOException {

        Long postId= commentRequestDto.getPost_id();
        String content = commentRequestDto.getContent();

        // 데이터 수신 확인용
        log.info("postId={}, content={}",postId,content);

        // comment 객체 생성시 필요한 데이터
        BoardPost boardPost = boardPostService.findOne(postId);
        User user = (User)userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));

        // 입력한 데이터를 기반으로 comment 객체 생성
        Comment comment = storeComment(user,
                content, LocalDateTime.now(),boardPost);

        // postId에 해당되는 게시물에 comment 추가
        BoardPost post = boardPostRepository.findOne(postId);
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);

        postCommentRepository.save(comment);
    }

    // 특정 댓글 하나 불러오기
    public Comment readPostCommnet(Long comment_id){
        return postCommentRepository.findOne(comment_id);
    }

    // 특정 댓글 수정
    public void updatePostComment(String token, Long comment_id, String content){
        User user = (User)userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        Long user_id=user.getId();

        Comment comment = postCommentRepository.findOne(comment_id);

        // 댓글 수정을 요청한 사용자가, 해당 댓글 작성자와 동일하다면 수정
        if(user_id==comment.getUser().getId()){
            postCommentRepository.update(comment_id,content);
        }
    }

    // 특정 댓글 삭제
    public void deletePostComment(String token, Long comment_id){
        User user = (User)userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        Long user_id=user.getId();

        Comment comment = postCommentRepository.findOne(comment_id);

        // 댓글 삭제를 요청한 사용자가, 해당 댓글 작성자와 동일하다면 삭제
        if(user_id==comment.getUser().getId()){
            postCommentRepository.delete(comment_id);
        }
    }

    // 특정 게시판의 모든 댓글 목록 반환
    public List<Comment> readPostCommentListByPostId(Long post_id){
        return postCommentRepository.findAllByPostId(post_id);
    }

    // 특정 사용자의 모든 댓글 목록 반환
    public List<Comment> readPostCommentListByUserId(Long user_id){
        return postCommentRepository.findAllByUserId(user_id);
    }
    
    // 댓글 생성 빌더
    public Comment storeComment(User user, String content,
                                LocalDateTime localDateTime, BoardPost boardPost){
        return Comment.builder()
                .user(user)
                .content(content)
                .commentDate(localDateTime)
                .likeCount(0)
                .boardPost(boardPost)
                .build();
    }
}
