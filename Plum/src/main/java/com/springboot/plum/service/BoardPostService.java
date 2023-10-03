package com.springboot.plum.service;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.dto.BoardPostReadDto;
import com.springboot.plum.data.dto.UpdateBoardPostDTO;
import com.springboot.plum.data.entity.*;
import com.springboot.plum.data.form.BoardAddForm;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.repository.PostCommentRepository;
import com.springboot.plum.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardPostService {

    private final AttachmentService attachmentService;  // 컴파일 시점에서 생성자를 통해 주입을 해주는지 체크를 해줄수 있기때문에 final 쓰면 좋다.
    private final BoardPostRepository boardPostRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private String imagesFolderName="/images/";
    public void createPost(HttpServletRequest request,List<MultipartFile> files) throws IOException {
        BoardAddForm boardAddForm = new BoardAddForm(request.getParameter("title"),
                request.getParameter("content"),
                files,null);

        NoticeBoard noticeBoard = noticeBoardRepository.findOne(request.getParameter("category"));

        String token = jwtTokenProvider.resolveToken(request);
        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto((User)user,noticeBoard);
        post(boardPostDto);
    }

    public BoardPostReadDto readPost(Long post_id){
        BoardPost boardPost = findOne(post_id);

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

    public void updatePost(UpdateBoardPostDTO updateBoardPostDTO,long post_id){
        String category = updateBoardPostDTO.getCategory();
        String title = updateBoardPostDTO.getTitle();
        String content = updateBoardPostDTO.getContent();

        boardPostRepository.update(post_id, category, title, content);
    }

    // 특정 게시판의 검색어가 포함된 게시글 10개를 페이지 번호에 맞게 반환
    public List<BoardPost> searchBoardList(String category,String keyword,int pageNum,String type) {
        if(keyword==null) keyword="";
        log.info("category={}, type={}, keyword={}, pageNum={}",category,type,keyword,pageNum);

        List<BoardPost> list = bringBoardsByKeword(category,keyword,type,pageNum);
        System.out.println("list size="+list.size());
        for(BoardPost x:list){
            System.out.println(x.toString());
        }
        return bringBoardsByKeword(category,keyword,type,pageNum);
    }

    public BoardPost post(BoardPostDto boardPostDto) throws IOException {
        BoardPost board = boardPostDto.createBoard();
        List<Attachment> attachments = attachmentService.saveAttachments(boardPostDto.getAttachmentFiles(),board);

        board.setAttachments(attachments);

        return boardPostRepository.save(board);
    }

    public BoardPost findOne(Long id){
        return boardPostRepository.findOne(id);
    }
    
    // 특정 게시글을 접근한 이용자가, 작성자와 동일한지 여부를 알려줌
    public boolean isWriter(HttpServletRequest request, long post_id){
        String token = jwtTokenProvider.resolveToken(request);
        User user = (User)userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        String username = user.getName();

        log.info("token={}, username={}",token,username);
        BoardPost post = boardPostRepository.findOne(post_id);

        if(post.getWriter().equals(username)) return true;
        else return false;
    }

    // 특정 게시판의 모든 게시물들을 호출
    public List<BoardPost> bringOneBoardPostList(String category){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.findOneBoardPostList(noticeBoard);
    }

    // 특정 게시판의 게시글 10개를 페이지 번호에 맞게 호출
    public List<BoardPost> bringOneBoardPostListPage(String category,int pageNum){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.findOneBoardPostListPage(noticeBoard,pageNum);
    }

    // 특정 게시판의 게시글 10개를 페이지 번호에 맞게 반환
    public long bringBoardCount(String category){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.boardCount(noticeBoard);
    }

    // 특정 게시판의 검색어에 알맞는 게시글 '개수' 반환
    public long bringBoardCountByKeyword(String category,String keyword, String type){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.boardCountByKeyword(noticeBoard,keyword,type);
    }

    // 특정 게시판의 검색어에 알맞는 게시글 반환
    public List<BoardPost> bringBoardsByKeword(String category,String keyword,String type,int pageNum){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.findBoardsByKeword(noticeBoard,keyword,type,pageNum);
    }
}
