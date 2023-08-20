package com.springboot.plum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.component.FileStore;
import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.CommentRequestDto;
import com.springboot.plum.data.dto.TestDto;
import com.springboot.plum.data.entity.*;
import com.springboot.plum.data.form.BoardAddForm;
import com.springboot.plum.data.repository.AttachmentRepository;
import com.springboot.plum.data.repository.BoardPostRepository;
import com.springboot.plum.data.repository.NoticeBoardRepository;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.CommentService;
import com.springboot.plum.service.impl.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("user/boardPost")
@RequiredArgsConstructor
@ResponseBody
public class BoardPostController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardPostService boardPostService;
    private final FileStore fileStore;
    private final AttachmentRepository attachmentRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final ObjectMapper objectMapper;
    private final PostCommentService postCommentService;

    private String imagesFolderName="/images/";

    // 게시글 작성하면 제목, 내용, 이미지들을 DB에 저장함
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public void AxiosFileTest (
        HttpServletRequest request,
        @RequestParam(value="file", required=false) List<MultipartFile> files) throws SQLException,IOException {

        BoardAddForm boardAddForm = new BoardAddForm(request.getParameter("title"),
                request.getParameter("content"),
                files,null);

        NoticeBoard noticeBoard = noticeBoardRepository.findOne(request.getParameter("category"));

        String token = jwtTokenProvider.resolveToken(request);
        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto((User)user,noticeBoard);
        BoardPost boardPost = boardPostService.post(boardPostDto);
    }

    @PostMapping(value ="/postLoad")
    public void processImg(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="post_id", required=false) Long postId) throws IOException {
        System.out.println(request.getParameter("post_id"));
        System.out.println("postId="+postId);
        System.out.println(request.getHeader("Authorization"));
        System.out.println(postId.getClass());
        BoardPost boardPost = boardPostService.findOne(postId);

        List<String> imagesURL = new ArrayList<>();
        List<Attachment> attachments = boardPost.getAttachments();
        for(Attachment attachment:attachments){
            imagesURL.add(imagesFolderName+attachment.getStoreFilename());
        }

        List<Comment> comments = boardPost.getComments();

        System.out.println(boardPost.getUser().getName());

        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
                boardPost.getContent(),boardPost.getNoticeBoard(), imagesURL,comments);

        System.out.println(boardPostDto.getNoticeBoardName());
        System.out.println(boardPostDto.getContent());
        System.out.println(boardPostDto.getTitle());
        //System.out.println(boardPostDto.getWriter());


        String jsonResponse = objectMapper.writeValueAsString(boardPostDto);

        // JSON 응답을 위해 헤더 설정

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 작성
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
//
//        attachmentRepository.findAll();
//        return new ResponseEntity<>(boardPostDto, headers, HttpStatus.OK);
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

        //
        return new ResponseEntity<>(commentRequestDto, HttpStatus.OK);
    }

//    @PostMapping(value ="/postLoad")
//    public List<String> processImg(HttpServletRequest request,@RequestParam(value="post_id", required=false) Long postId){
//        System.out.println(request.getParameter("post_id"));
//        System.out.println("postId="+postId);
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println(postId.getClass());
//        BoardPost boardPost = boardPostService.findOne(postId);
//
//        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
//                boardPost.getContent(),boardPost.getNoticeBoard(), boardPost.getAttachments());
//
//        List<String> imagesURL = new ArrayList<>();
//        List<Attachment> attachments = boardPost.getAttachments();
//        for(Attachment attachment:attachments){
//            imagesURL.add(imagesFolderName+attachment.getStoreFilename());
//        }
//
//        attachmentRepository.findAll();
//        return imagesURL;
//    }

    // 특정 이미지 로드
//    @PostMapping(value ="/postLoad")
//    public BoardPostReadDto processImg(HttpServletRequest request,@RequestParam(value="post_id", required=false) Long postId){
//        System.out.println(request.getParameter("post_id"));
//        System.out.println("postId="+postId);
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println(postId.getClass());
//        BoardPost boardPost = boardPostService.findOne(postId);
//
//        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
//        boardPost.getContent(),boardPost.getNoticeBoard(), boardPost.getAttachments());
//
//        attachmentRepository.findAll();
//        return boardPostDto;
//    }
}
