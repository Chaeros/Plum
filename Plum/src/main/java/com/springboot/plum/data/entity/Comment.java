package com.springboot.plum.data.entity;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="boardpost_id")
    private BoardPost boardPost;

    private String writer;

    private String content;

    private Integer likeCount;

    private LocalDateTime commentDate;
}