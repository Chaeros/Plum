package com.springboot.plum.data.dto;

import com.springboot.plum.data.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class BoardPostReadDto {
    private User user;
    private String writer;
    private String title;
    private String content;
    private String noticeBoardName;
    private LocalDateTime writeTime;
    private List<String> imagesURL;
    private List<ReadCommentDto> comments;


    public BoardPostReadDto(User user, String title, String content,
           NoticeBoard noticeBoard,LocalDateTime writeTime, List<String> imagesURL, List<ReadCommentDto> comments) {
        this.user = user;
        this.writer=user.getName();
        this.title = title;
        this.content = content;
        String boardName=noticeBoard.getName();
        this.noticeBoardName = boardName;
        this.writeTime = writeTime;
        this.imagesURL = imagesURL;
        this.comments = comments;
    }
}
