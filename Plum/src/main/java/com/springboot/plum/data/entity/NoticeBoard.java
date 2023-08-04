package com.springboot.plum.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class NoticeBoard {
    @Id @GeneratedValue
    @Column(name="noticeboard_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "noticeBoard" , cascade = CascadeType.ALL)
    private List<MyBoard> myBoards = new ArrayList<>();

    @OneToMany(mappedBy = "noticeBoard" , cascade = CascadeType.ALL)
    private List<BoardPost> boardPosts = new ArrayList<>();

}
