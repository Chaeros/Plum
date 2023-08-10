package com.springboot.plum.service;

import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.repository.BoardPostRepository;
import com.springboot.plum.data.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BoardPostService {

    private final AttachmentService attachmentService;  // 컴파일 시점에서 생성자를 통해 주입을 해주는지 체크를 해줄수 있기때문에 final 쓰면 좋다.
    private final BoardPostRepository boardPostRepository;

    @Autowired
    public BoardPostService(AttachmentService attachmentService,
                            BoardPostRepository boardPostRepository) {
        this.attachmentService = attachmentService;
        this.boardPostRepository = boardPostRepository;
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
}
