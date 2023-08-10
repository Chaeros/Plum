package com.springboot.plum.data.entity;

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

    private Long writerId;

    private boolean isDeleted;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")   //외래키 이름 설정
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="noticeboard_id", referencedColumnName = "noticeboard_id")
    private NoticeBoard noticeBoard;

    @OneToMany(mappedBy = "boardPost" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "boardPost", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    public BoardPost(){

    }

    @Builder
    public BoardPost(Long id, String title, String content, LocalDateTime writeTime, User writer,
                     int likeCount, boolean isDeleted, List<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writeTime = writeTime;
        Long tempWriterId=writer.getId();
        this.writerId = tempWriterId;
        this.likeCount = likeCount;
        this.attachments = attachments;
        this.isDeleted = isDeleted;
    }
}
