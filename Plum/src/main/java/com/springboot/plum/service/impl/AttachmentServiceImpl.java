package com.springboot.plum.service.impl;

import com.springboot.plum.data.component.FileStore;
import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.repository.AttachmentRepository;
import com.springboot.plum.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final FileStore fileStore;

    public Map<String,Object> createAttachment(HttpServletRequest request, MultipartFile[] files){
        log.info("category={}, title={}, content={}",request.getParameter("category"),
                request.getParameter("title"),request.getParameter("content"));

        Map<String,Object> resultMap = new HashMap<String,Object>();
        String FileNames ="";
        log.info("paraMap={}",files[0]);

        String filepath = "D:/saveFolder/";
        for (MultipartFile mf : files) {

            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
            long fileSize = mf.getSize(); // 파일 사이즈
            log.info("originFileName={}, fileSize={}",originFileName,fileSize);

            String safeFile =System.currentTimeMillis() + originFileName;

            FileNames = FileNames+","+safeFile;
            try {
                File f1 = new File(filepath+safeFile);
                mf.transferTo(f1);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        FileNames = FileNames.substring(1);
        log.info("FileNames={}",FileNames);
        resultMap.put("JavaData", paramMap);
        return resultMap;
    }

    public List<Attachment> saveAttachments(Map<AttachmentType,
            List<MultipartFile>> multipartFileListMap, BoardPost boardPost) throws IOException {
        // imageFiles에는 IMAGE 파일들만 구분하여 넣는다.
        List<Attachment> imageFiles = fileStore.storeFiles(multipartFileListMap.get(AttachmentType.IMAGE), AttachmentType.IMAGE,boardPost);
        // generalFiles에는 GENERAL 파일들만 구분하여 넣는다.
        List<Attachment> generalFiles = fileStore.storeFiles(multipartFileListMap.get(AttachmentType.GENERAL), AttachmentType.GENERAL,boardPost);
        List<Attachment> result =
                Stream.of(imageFiles, generalFiles)
                .flatMap(f -> f.stream())
                .collect(Collectors.toList());
        return result;
    }

    // Repository에서 모든 파일을 불러온 뒤
    // 첨부파일 타입에 따라(IMAGE, GENERAL) 구분하여 맵에 저장한뒤 반환함
    public Map<AttachmentType, List<Attachment>> findAttachments() {
        List<Attachment> attachments = attachmentRepository.findAll();
        Map<AttachmentType, List<Attachment>> result = attachments.stream()
                .collect(Collectors.groupingBy(Attachment::getAttachmentType));

        return result;
    }
}