package com.springboot.plum.controller;

import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.UpdateBoardPostDTO;
import com.springboot.plum.data.entity.*;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.service.BoardPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("/boardPost")
@RequiredArgsConstructor
@Slf4j
public class BoardPostController {

    private final BoardPostService boardPostService;
    private final BoardPostRepository boardPostRepository;

    // 게시글 생성(저장)
    @PostMapping()
    public void createPost (HttpServletRequest request,
        @RequestParam(value="file", required=false) List<MultipartFile> files) throws IOException {
        log.info("[post create]");
        boardPostService.createPost(request,files);
    }

    // 게시글 읽기(불러오기)
    @GetMapping("/{post_id}")
    public BoardPostReadDto readPost(@PathVariable Long post_id){
        log.info("[post read] post_id={}",post_id);
        return boardPostService.readPost(post_id);
    }

    // 게시글 수정
    @PutMapping("/{post_id}")
    public void updadtePost(@RequestBody UpdateBoardPostDTO updateBoardPostDTO,
                            @PathVariable long post_id){
        log.info("[post update] post_id={}",post_id);
        boardPostService.updatePost(updateBoardPostDTO,post_id);
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public void deletePost(@PathVariable long post_id){
        log.info("[post delete] post_id={}",post_id);
        boardPostRepository.delete(post_id);
    }

    // 특정 게시판의 모든 게시글 읽기(불러오기)
    @GetMapping()
    public List<BoardPost> readBoardList(@RequestBody String category){
        log.info("[readBoardList] category={}",category);
        return boardPostService.bringOneBoardPostList(category);
    }

    // 특정 게시판의 검색어가 포함된 게시글 10개를 페이지 번호에 맞게 반환
    @GetMapping(value = {"/boardList/{category}/{pageNum}/{keyword}","/boardList/{category}/{pageNum}"})
    public List<BoardPost> searchBoardList(@PathVariable String category,
                                           @PathVariable(required = false) String keyword,
                                           @PathVariable int pageNum,
                                           @RequestParam String type){
        log.info("category={}, type={}, keyword={}, pageNum={}",category,type,keyword,pageNum);
        return boardPostService.searchBoardList(category,keyword,pageNum,type);
    }

    // 해당 카테고리의 총 게시글 갯수 반환
    @GetMapping(value = "/boardsCount")
    public long boardsCount(@RequestParam String category){
        log.info("category={}",category);
        return boardPostService.bringBoardCount(category);
    }

    // 해당 카테고리의 검색어에 대응되는 게시글 개수 반환
    @GetMapping(value = {"/boardsCount/{category}/{keyword}","/boardsCount/{category}/"})
    public long boardsCountByKeyword(@PathVariable String category,
                                     @PathVariable(required = false) String keyword,
                                     @RequestParam String type){
        if(keyword==null) keyword="";
        log.info("category={}, type={}, keyword={}",category,type,keyword);
        return boardPostService.bringBoardCountByKeyword(category,keyword,type);
    }

    // 게시글 조회수 1 증가
    @PostMapping("/postViewsUp/{post_id}")
    public void postViewsUp(@PathVariable long post_id){
        boardPostRepository.increaseViews(post_id);
    }

    // 특정 게시글을 접근한 이용자가, 작성자와 동일한지 여부를 알려주는 api
    @PostMapping(value ="/isWriter")
    public boolean isWriter(HttpServletRequest request, @RequestParam long post_id){
        log.info("[/isWriter]작성자 검증");
        return boardPostService.isWriter(request,post_id);
    }
}
