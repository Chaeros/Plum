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

        List<BoardPost> list = boardPostService.bringBoardsByKeword(category,keyword,type,pageNum);
        System.out.println("list size="+list.size());
        for(BoardPost x:list){
            System.out.println(x.toString());
        }
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

}
