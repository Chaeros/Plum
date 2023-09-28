package com.springboot.plum.service;

import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.repository.BoardPostRepository;
import com.springboot.plum.repository.CommentRepository;
import com.springboot.plum.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BoardPostService {

    private final AttachmentService attachmentService;  // 컴파일 시점에서 생성자를 통해 주입을 해주는지 체크를 해줄수 있기때문에 final 쓰면 좋다.
    private final BoardPostRepository boardPostRepository;
    private final CommentRepository commentRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    @Autowired
    public BoardPostService(AttachmentService attachmentService,
                            BoardPostRepository boardPostRepository, CommentRepository commentRepository, NoticeBoardRepository noticeBoardRepository) {
        this.attachmentService = attachmentService;
        this.boardPostRepository = boardPostRepository;
        this.commentRepository = commentRepository;
        this.noticeBoardRepository = noticeBoardRepository;
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

    public Comment addComment(Long id, Comment comment){
        BoardPost post = boardPostRepository.findOne(id);
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);

        return commentRepository.save(comment);
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

    // 특정 게시판의 검색어에 알맞는 게시글 개수 반환
    public long bringBoardCountByKeyword(String category,String keyword, String type){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.boardCountByKeyword(noticeBoard,keyword,type);
    }

    public List<BoardPost> bringBoardsByKeword(String category,String keyword,String type,int pageNum){
        NoticeBoard noticeBoard =noticeBoardRepository.findOne(category);
        return boardPostRepository.findBoardsByKeword(noticeBoard,keyword,type,pageNum);
    }

}
