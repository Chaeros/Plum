package com.springboot.plum.data.dto;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReadCommentDto {
    private Long comment_id;
    private String content;
    private Integer likeCount;
    private Long post_id;
    private UserDto writer_user;
    private LocalDateTime commentDate;

    public ReadCommentDto(Long comment_id, String content, Integer likeCount,
                          Long post_id, User writer_user, LocalDateTime commentDate) {
        this.comment_id = comment_id;
        this.content = content;
        this.likeCount = likeCount;
        this.post_id = post_id;
        this.writer_user = new UserDto(writer_user);
        this.commentDate = commentDate;
    }

    public ReadCommentDto(Comment comment){
        this.comment_id = comment.getId();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.post_id = comment.getBoardPost().getId();
        this.writer_user = new UserDto(comment.getUser());
        this.commentDate = comment.getCommentDate();
    }
}
