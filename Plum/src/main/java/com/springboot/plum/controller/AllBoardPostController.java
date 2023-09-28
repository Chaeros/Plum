package com.springboot.plum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.service.BoardPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/boardPost")
@RequiredArgsConstructor
@ResponseBody
@Slf4j
public class AllBoardPostController {

    private final BoardPostService boardPostService;
    private final ObjectMapper objectMapper;
    private final BoardPostRepository boardPostRepository;

    private String imagesFolderName="/images/";

    // 특정 게시판의 모든 게시글들을 반환시킴
    @PostMapping(value = "/boardList", consumes="application/json;")
    public List<BoardPost> boardList(@RequestBody HashMap<String, Object> map){
        // 콘솔 확인용
        System.out.println("/boardList:category="+map.get("category"));
        String category = (String)map.get("category");
        return boardPostService.bringOneBoardPostList(category);
    }

    // 특정 게시판의 게시글 10개를 페이지 번호에 맞게 반환
    @GetMapping(value = "/boardList")
    public List<BoardPost> boardList(@RequestParam String category,
                                     @RequestParam int pageNum){
        log.info("category={}, pageNum={}",category,pageNum);

        return boardPostService.bringOneBoardPostListPage(category,pageNum);
    }

    // 특정 게시판의 검색어가 포함된 게시글 10개를 페이지 번호에 맞게 반환
    @GetMapping(value = {"/boardList/{category}/{pageNum}/{keyword}","/boardList/{category}/{pageNum}"})
    public List<BoardPost> searchBoardList(@PathVariable String category,
                                           @PathVariable(required = false) String keyword,
                                           @PathVariable int pageNum,
                                           @RequestParam String type){
        if(keyword==null) keyword="";
        log.info("category={}, type={}, keyword={}, pageNum={}",category,type,keyword,pageNum);
        return boardPostService.bringBoardsByKeword(category,keyword,type,pageNum);
    }

    // 해당 카테고리의 게시글 갯수 반환
    @GetMapping(value = "/boardsCount")
    public long boardsCount(@RequestParam String category){
        log.info("category={}",category);

        return boardPostService.bringBoardCount(category);
    }

    @GetMapping(value = {"/boardsCount/{category}/{keyword}","/boardsCount/{category}/"})
    public long boardsCountByKeyword(@PathVariable String category,
                                           @PathVariable(required = false) String keyword,
                                           @RequestParam String type){
        if(keyword==null) keyword="";
        log.info("category={}, type={}, keyword={}",category,type,keyword);
        return boardPostService.bringBoardCountByKeyword(category,keyword,type);
    }

    @GetMapping(value ="/postLoad")
    public void processImg(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="post_id", required=false) Long postId) throws IOException {
        log.info("Authorization={},",request.getHeader("Authorization"));
        BoardPost boardPost = boardPostService.findOne(postId);

        List<String> imagesURL = new ArrayList<>();
        List<Attachment> attachments = boardPost.getAttachments();
        for(Attachment attachment:attachments){
            imagesURL.add(imagesFolderName+attachment.getStoreFilename());
        }

        List<Comment> comments = boardPost.getComments();


        BoardPostReadDto boardPostDto = new BoardPostReadDto(boardPost.getUser(), boardPost.getTitle(),
                boardPost.getContent(),boardPost.getNoticeBoard(), imagesURL,comments);

        String jsonResponse = objectMapper.writeValueAsString(boardPostDto);

        // JSON 응답을 위해 헤더 설정

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 작성
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);

        // 조회수 증가
        boardPostRepository.increaseViews(postId);
//
//        attachmentRepository.findAll();
//        return new ResponseEntity<>(boardPostDto, headers, HttpStatus.OK);
    }
}
