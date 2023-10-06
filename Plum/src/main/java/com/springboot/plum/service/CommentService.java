package com.springboot.plum.service;

import com.springboot.plum.data.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public interface CommentService {
    public Comment storeComment(User user, String content,
                                LocalDateTime localDateTime, BoardPost boardPost) throws IOException;

}
