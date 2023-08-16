package com.springboot.plum.service;

import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.repository.BoardPostRepository;
import com.springboot.plum.data.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BoardPostService {

    private final AttachmentService attachmentService;  // 컴파일 시점에서 생성자를 통해 주입을 해주는지 체크를 해줄수 있기때문에 final 쓰면 좋다.
    private final BoardPostRepository boardPostRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BoardPostService(AttachmentService attachmentService,
                            BoardPostRepository boardPostRepository, CommentRepository commentRepository) {
        this.attachmentService = attachmentService;
        this.boardPostRepository = boardPostRepository;
        this.commentRepository = commentRepository;
    }

    public BoardPost post(BoardPostDto boardPostDto) throws IOException {
        BoardPost board = boardPostDto.createBoard();
        List<Attachment> attachments = attachmentService.saveAttachments(boardPostDto.getAttachmentFiles(),board);
//        for (Attachment attachment : attachments) {
//            log.info(attachment.getOriginFilename());
//        }
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
}
