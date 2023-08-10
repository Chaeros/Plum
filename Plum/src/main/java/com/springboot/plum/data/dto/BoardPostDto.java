package com.springboot.plum.data.dto;

import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardPostDto {
    private User writer;
    private String title;
    private String content;
    private Map<AttachmentType, List<MultipartFile>> attachmentFiles = new ConcurrentHashMap<>();

    @Builder
    public BoardPostDto(User writer, String title, String content, Map<AttachmentType, List<MultipartFile>> attachmentFiles) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.attachmentFiles = attachmentFiles;
    }

    public BoardPost createBoard() {
        return BoardPost.builder()
                .writer(writer)
                .title(title)
                .writeTime(LocalDateTime.now())
                .content(content)
                .attachments(new ArrayList<>())
                .isDeleted(false)
                .likeCount(0)
                .build();
    }
}