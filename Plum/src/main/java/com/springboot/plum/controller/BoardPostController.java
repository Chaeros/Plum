package com.springboot.plum.controller;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.component.FileStore;
import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.entity.*;
import com.springboot.plum.data.form.BoardAddForm;
import com.springboot.plum.data.repository.AttachmentRepository;
import com.springboot.plum.data.repository.BoardPostRepository;
import com.springboot.plum.data.repository.NoticeBoardRepository;
import com.springboot.plum.service.BoardPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("user/boardPost")
@RequiredArgsConstructor
public class BoardPostController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardPostService boardPostService;
    private final FileStore fileStore;
    private final AttachmentRepository attachmentRepository;
    private final NoticeBoardRepository noticeBoardRepository;

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

    // 특정 이미지 로드
    @PostMapping(value ="/postLoad")
    public BoardPostReadDto processImg(HttpServletRequest request,@RequestParam(value="post_id", required=false) Long postId){
        System.out.println(request.getParameter("post_id"));
        System.out.println("postId="+postId);
        BoardPost boardPost = boardPostService.findOne(postId);

        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
        boardPost.getContent(),boardPost.getNoticeBoard(), boardPost.getAttachments());

        attachmentRepository.findAll();
        return boardPostDto;
    }
}
