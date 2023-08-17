package com.springboot.plum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.component.FileStore;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.repository.AttachmentRepository;
import com.springboot.plum.data.repository.NoticeBoardRepository;
import com.springboot.plum.service.BoardPostService;
import com.springboot.plum.service.impl.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/boardPost")
@RequiredArgsConstructor
@ResponseBody
public class AllBoardPostController {

    private final BoardPostService boardPostService;
    private final ObjectMapper objectMapper;

    private String imagesFolderName="/images/";
    @GetMapping(value ="/postLoad")
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

}
