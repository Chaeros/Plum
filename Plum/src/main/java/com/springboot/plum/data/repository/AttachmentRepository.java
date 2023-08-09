package com.springboot.plum.data.repository;

import com.springboot.plum.data.entity.Attachment;
import com.springboot.plum.data.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttachmentRepository {
    //@PersistenceContext  // JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저로 주입해준다.
    private final EntityManager em;

    public Long save(Attachment attachment){ em.persist(attachment); return attachment.getId(); }  //영속성 컨테이너에 이 객체를 올린다.

    public Attachment findOne(Long id){ return em.find(Attachment.class,id); }

    public List<Attachment> findAll(){
        return em.createQuery("select m from Attachment m", Attachment.class)
                .getResultList();  //쿼리문, 반환할 타입    jpql 문법으로, from의 대상이 Member 테이블이 아니라 entity로 들어간다
    }
}
