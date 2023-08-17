package com.springboot.plum.data.repository;

import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class BoardPostRepository {
    //@PersistenceContext  // JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저로 주입해준다.
    private final EntityManager em;

    public BoardPost save(BoardPost boardPost){ em.persist(boardPost); return boardPost; }  //영속성 컨테이너에 이 객체를 올린다.

    public BoardPost findOne(Long id){ return em.find(BoardPost.class,id); }

    public List<BoardPost> findAll(){
        return em.createQuery("select m from BoardPost m", BoardPost.class)
                .getResultList();  //쿼리문, 반환할 타입    jpql 문법으로, from의 대상이 Member 테이블이 아니라 entity로 들어간다
    }

    public List<BoardPost> findOneBoardPostList(NoticeBoard noticeBoard){
        return em.createQuery("select m from BoardPost m where m.noticeBoard = :noticeBoard", BoardPost.class)
                .setParameter("noticeBoard", noticeBoard)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                .getResultList();
    }
}