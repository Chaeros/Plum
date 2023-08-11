package com.springboot.plum.data.entity;

import com.springboot.plum.data.dto.BoardPostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class NoticeBoard {
    @Id @GeneratedValue
    @Column(name="noticeboard_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "noticeBoard" , cascade = CascadeType.ALL)
    private List<MyBoard> myBoards = new ArrayList<>();

    @OneToMany(mappedBy = "noticeBoard" , cascade = CascadeType.ALL)
    private List<BoardPost> boardPosts = new ArrayList<>();
}
