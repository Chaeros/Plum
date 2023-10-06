package com.springboot.plum.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Builder // 찾아보기
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="boardpost_id")
    private BoardPost boardPost;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String content;

    private Integer likeCount;

    private LocalDateTime commentDate;

    @Builder
    public Comment(BoardPost boardPost, User user, String content,
                   Integer likeCount, LocalDateTime commentDate) {
        this.boardPost = boardPost;
        this.user = user;
        this.content = content;
        this.likeCount = likeCount;
        this.commentDate = commentDate;
    }

}
