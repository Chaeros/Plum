package com.springboot.plum.service;

import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.BoardPost;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AttachmentService {
    public List<Attachment> saveAttachments(Map<AttachmentType, List<MultipartFile>> multipartFileListMap, BoardPost board) throws IOException;
    public Map<AttachmentType, List<Attachment>> findAttachments();
}
