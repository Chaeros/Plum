package com.springboot.plum.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
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

    private String title;

    private String content;

    private LocalDateTime writeTime;

    private int likeCount;

    private boolean isDeleted;

    private String writer;
    
    private int views;  // 조회수

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")   //외래키 이름 설정
    private User user;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="noticeboard_id", referencedColumnName = "noticeboard_id")
    private NoticeBoard noticeBoard;

    @JsonManagedReference
    @OneToMany(mappedBy = "boardPost" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "boardPost", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    public BoardPost(){

    }

    @Builder
    public BoardPost(Long id, String title, String content, LocalDateTime writeTime, User writer,
                     int likeCount, boolean isDeleted, List<Attachment> attachments, NoticeBoard noticeBoard) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeTime = writeTime;
        this.user=writer;
        this.writer=user.getName();
        this.likeCount = likeCount;
        this.attachments = attachments;
        this.isDeleted = isDeleted;
        this.noticeBoard= noticeBoard;
    }

    public void increaseViews(){
        ++this.views;
    }
}