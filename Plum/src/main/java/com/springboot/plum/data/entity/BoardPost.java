package com.springboot.plum.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class BoardPost {
    @Id @GeneratedValue
    @Column(name="boardpost_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")   //외래키 이름 설정
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="noticeboard_id")
    private NoticeBoard noticeBoard;

    @OneToMany(mappedBy = "boardPost" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private String subject;

    private String content;

    private LocalDateTime postDate;

    private String writer;

    private int likeCount;
}
