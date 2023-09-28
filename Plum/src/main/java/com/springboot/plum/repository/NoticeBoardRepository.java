package com.springboot.plum.repository;

import com.springboot.plum.data.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeBoardRepository {
    //@PersistenceContext  // JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저로 주입해준다.
    private final EntityManager em;

    public Long save(NoticeBoard noticeBoard){ em.persist(noticeBoard); return noticeBoard.getId(); }  //영속성 컨테이너에 이 객체를 올린다.

    public NoticeBoard findOne(Long id){ return em.find(NoticeBoard.class,id); }

    public NoticeBoard findOne(String title){
        return em.createQuery("select m from NoticeBoard m where m.name = :title", NoticeBoard.class)
                .setParameter("title", title)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                .getSingleResult();
    }

    public List<NoticeBoard> findAll(){
        return em.createQuery("select m from NoticeBoard m", NoticeBoard.class)
                .getResultList();  //쿼리문, 반환할 타입    jpql 문법으로, from의 대상이 Member 테이블이 아니라 entity로 들어간다
    }
}
