package com.springboot.plum.controller;

import com.springboot.plum.service.impl.AttachmentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins="*")
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentServiceImpl attachmentService;

    // 첨부파일 생성
    @PostMapping()
    public Map<String,Object> createAttachment (HttpServletRequest request,
                                             @RequestParam(value="file", required=false) MultipartFile[] files){
        log.info("category={}, title={}, content={}",request.getParameter("category"),
                request.getParameter("title"),request.getParameter("content"));
        return attachmentService.createAttachment(request,files);
    }
}