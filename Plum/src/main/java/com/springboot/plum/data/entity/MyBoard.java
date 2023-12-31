package com.springboot.plum.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MyBoard {
    @Id @GeneratedValue
    @Column(name="myboard_id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")   //외래키 이름 설정
    private User user;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="noticeboard_id")
    private NoticeBoard noticeBoard;
}
