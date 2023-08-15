package com.springboot.plum.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="boardpost_id", referencedColumnName ="boardpost_id")
    private BoardPost boardPost;

    @Builder
    public Attachment(Long id, String originFilename, String storeFilename, AttachmentType attachmentType,
                      BoardPost boardPost){
        this.id=id;
        this.originFilename=originFilename;
        this.storeFilename=storeFilename;
        this.attachmentType=attachmentType;
        this.boardPost=boardPost;
    }



}
