package com.springboot.plum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.component.FileStore;
import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.dto.UpdateBoardPostDTO;
import com.springboot.plum.data.entity.*;
import com.springboot.plum.data.form.BoardAddForm;
import com.springboot.plum.repository.AttachmentRepository;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.repository.NoticeBoardRepository;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.impl.PostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/user/boardPost")
@RequiredArgsConstructor
@Slf4j
public class BoardPostController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardPostService boardPostService;
    private final FileStore fileStore;
    private final AttachmentRepository attachmentRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final ObjectMapper objectMapper;
    private final PostCommentService postCommentService;
    private final BoardPostRepository boardPostRepository;

    private String imagesFolderName="/images/";

    // 게시글 생성(저장)
    @PostMapping(value="/create")
    public void AxiosFileTest (
        HttpServletRequest request,
        @RequestParam(value="file", required=false) List<MultipartFile> files) throws SQLException,IOException {
        log.info("[post create]");

        BoardAddForm boardAddForm = new BoardAddForm(request.getParameter("title"),
                request.getParameter("content"),
                files,null);

        NoticeBoard noticeBoard = noticeBoardRepository.findOne(request.getParameter("category"));

        String token = jwtTokenProvider.resolveToken(request);
        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto((User)user,noticeBoard);
        BoardPost boardPost = boardPostService.post(boardPostDto);
    }

    // 게시글 읽기(불러오기)
    @GetMapping("/{post_id}")
    public BoardPostReadDto processImg(@PathVariable Long post_id) throws IOException {
        log.info("[post read] post_id={}",post_id);
        BoardPost boardPost = boardPostService.findOne(post_id);

        List<String> imagesURL = new ArrayList<>();
        List<Attachment> attachments = boardPost.getAttachments();
        for(Attachment attachment:attachments){
            imagesURL.add(imagesFolderName+attachment.getStoreFilename());
        }

        List<Comment> comments = boardPost.getComments();

        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
                boardPost.getContent(),boardPost.getNoticeBoard(),boardPost.getWriteTime(), imagesURL,comments);

        return boardPostDto;
    }

    // 게시글 수정
    @PutMapping("/{post_id}")
    public void updadtePost(@RequestBody UpdateBoardPostDTO updateBoardPostDTO,
                            @PathVariable long post_id){
        log.info("[post update] post_id={}",post_id);

        BoardPost boardPost = boardPostService.findOne(post_id);
        String category = updateBoardPostDTO.getCategory();
        String title = updateBoardPostDTO.getTitle();
        String content = updateBoardPostDTO.getContent();

        boardPostRepository.update(post_id,category,title,content);
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public void deletePost(@PathVariable long post_id){
        log.info("[post delete] post_id={}",post_id);
        boardPostRepository.delete(post_id);
    }

    // 게시글 조회수 1 증가
    @PostMapping("/postViewsUp/{post_id}")
    public void postViewsUp(@PathVariable long post_id){
        boardPostRepository.increaseViews(post_id);
    }

    @PostMapping(value ="/addComment", consumes="application/json;")
    public ResponseEntity<CommentRequestDto> postListList(@RequestBody CommentRequestDto commentRequestDto) throws IOException {

        Long postId= commentRequestDto.getPost_id();
        String content = commentRequestDto.getContent();

        // 데이터 수신 확인용
        System.out.println("postId="+commentRequestDto.getPost_id());
        System.out.println("content="+commentRequestDto.getContent());

        // comment 객체 생성시 필요한 데이터
        BoardPost boardPost = boardPostService.findOne(postId);
        String writer = boardPost.getUser().getName();

        // 입력한 데이터를 기반으로 comment 객체 생성
        Comment comment = postCommentService.storeComment(writer,
                content, LocalDateTime.now(),boardPost);
        
        // postId에 해당되는 게시물에 comment 추가
        boardPostService.addComment(postId,comment);

        return new ResponseEntity<>(commentRequestDto, HttpStatus.OK);
    }

    // 특정 게시글을 접근한 이용자가, 작성자와 동일한지 여부를 알려주는 api
    @PostMapping(value ="/isWriter")
    public boolean isWriter(HttpServletRequest request, @RequestParam long post_id){
        log.info("작성자 검증");
        String token = jwtTokenProvider.resolveToken(request);
        User user = (User)userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        String username = user.getName();

        log.info("token={}, username={}",token,username);

        BoardPost post = boardPostRepository.findOne(post_id);

        if(post.getWriter().equals(username)) return true;
        else return false;
    }
}
