package com.springboot.plum.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.plum.data.entity.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class BoardPostReadDto {
    private User user;
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String noticeBoardName;
    private int views;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime writeDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private List<String> imagesURL;
    private List<ReadCommentDto> comments;

    public BoardPostReadDto(BoardPost boardPost){
        this.id = boardPost.getId();
        this.writer = boardPost.getWriter();
        this.title = boardPost.getTitle();
        this.content = boardPost.getContent();
        this.noticeBoardName = boardPost.getNoticeBoard().getName();
        this.views = boardPost.getViews();
        this.writeDate = boardPost.getWriteTime();
        this.writeTime = boardPost.getWriteTime();
    }

    public BoardPostReadDto(User user, String title, String content,
           NoticeBoard noticeBoard,LocalDateTime writeDate, LocalDateTime writeTime,
                            List<String> imagesURL, List<ReadCommentDto> comments) {
        this.user = user;
        this.writer=user.getName();
        this.title = title;
        this.content = content;
        String boardName=noticeBoard.getName();
        this.noticeBoardName = boardName;
        this.writeDate=writeDate;
        this.writeTime = writeTime;
        this.imagesURL = imagesURL;
        this.comments = comments;
    }
}
