package com.springboot.plum.data.dto;

import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateBoardPostDTO {
    private String category;
    private String title;
    private String content;


    public UpdateBoardPostDTO(String category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

}
