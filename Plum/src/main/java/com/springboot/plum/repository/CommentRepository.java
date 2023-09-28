package com.springboot.plum.repository;

import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentRepository {
    //@PersistenceContext  // JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저로 주입해준다.
    private final EntityManager em;

    public Comment save(Comment comment){ em.persist(comment); return comment; }  //영속성 컨테이너에 이 객체를 올린다.

    public Comment findOne(Long id){ return em.find(Comment.class,id); }

    public List<Comment> findAll(){
        return em.createQuery("select m from Comment m", Comment.class)
                .getResultList();  //쿼리문, 반환할 타입    jpql 문법으로, from의 대상이 Member 테이블이 아니라 entity로 들어간다
    }

}
