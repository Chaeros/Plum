package com.springboot.plum.data.component;

import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.AttachmentType;
import com.springboot.plum.data.entity.BoardPost;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    //@Value("${file.dir}/")
    private String fileDirPath=new String("D:/SpringProject/files2/");

    public List<Attachment> storeFiles(List<MultipartFile> multipartFiles, AttachmentType attachmentType,
                                       BoardPost boardPost) throws IOException {
        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                attachments.add(storeFile(multipartFile, attachmentType, boardPost));
            }
        }

        return attachments;
    }

    public Attachment storeFile(MultipartFile multipartFile, AttachmentType attachmentType,
                                BoardPost boardPost) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        //생성한 파일의 경로로 transferTo 메소드를 통해 이동시키고 업로드함
        multipartFile.transferTo(new File(createPath(storeFilename, attachmentType)));

        return Attachment.builder()
                .originFilename(originalFilename)
                .storeFilename(storeFilename)
                .attachmentType(attachmentType)
                .boardPost(boardPost)
                .build();
    }

    public String createPath(String storeFilename, AttachmentType attachmentType) {
        String viaPath = (attachmentType == AttachmentType.IMAGE) ? "images/" : "generals/";
        return fileDirPath + viaPath + storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        //UUID(Universally Unique IDentifier)는 네트워크상에서 고유성을 보장하는 ID를 만들기 위한 표준 규약
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

}