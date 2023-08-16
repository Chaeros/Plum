package com.springboot.plum.service;

import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public interface CommentService {
    public Comment storeComment(String writer, String content,
          LocalDateTime localDateTime, BoardPost boardPost) throws IOException;

}
