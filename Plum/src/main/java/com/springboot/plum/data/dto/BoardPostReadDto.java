package com.springboot.plum.data.dto;

import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardPostReadDto {
    private User writer;
    private String title;
    private String content;
    private NoticeBoard noticeBoard;
    private List<Attachment> attachments;

    public BoardPostReadDto(User writer, String title, String content,
           NoticeBoard noticeBoard, List<Attachment> attachments) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.noticeBoard = noticeBoard;
        this.attachments = attachments;
    }
}
