package com.springboot.plum.data.form;

import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardAddForm {
    private String title;
    private String content;
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> generalFiles;

    @Builder
    public BoardAddForm(String title, String content, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.title = title;
        this.content = content;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }


    public BoardPostDto createBoardPostDto(User user, NoticeBoard noticeBoard) {
        Map<AttachmentType, List<MultipartFile>> attachments = getAttachmentTypeListMap();
        return BoardPostDto.builder()
                .title(title)
                .writer(user)
                .content(content)
                .attachmentFiles(attachments)
                .noticeBoard(noticeBoard)
                .build();
    }

    private Map<AttachmentType, List<MultipartFile>> getAttachmentTypeListMap() {
        Map<AttachmentType, List<MultipartFile>> attachments = new ConcurrentHashMap<>();
        attachments.put(AttachmentType.IMAGE, imageFiles);
        attachments.put(AttachmentType.GENERAL, generalFiles);
        return attachments;
    }
}