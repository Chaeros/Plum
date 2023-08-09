package com.springboot.plum.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue
    @Column(name="attachment_id")
    private Long id;

    private String originFilename;
    private String storeFilename;
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="boardpost_id")
    private BoardPost boardPost;

    @Builder
    public Attachment(Long id, String originFilename, String storeFilename, AttachmentType attachmentType){
        this.id=id;
        this.originFilename=originFilename;
        this.storeFilename=storeFilename;
        this.attachmentType=attachmentType;
    }



}
