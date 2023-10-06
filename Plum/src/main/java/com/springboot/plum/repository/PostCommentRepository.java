package com.springboot.plum.repository;

import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.Comment;
import com.springboot.plum.data.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostCommentRepository {
    //@PersistenceContext  // JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저로 주입해준다.
    private final EntityManager em;

    public Comment save(Comment comment){ em.persist(comment); return comment; }

    public Comment findOne(Long id){ return em.find(Comment.class,id); }

    public void update(Long id,String content){
        Comment comment = em.find(Comment.class,id);
        comment.setContent(content);
    }

    public void delete(Long id){ em.remove(findOne(id)); }

    public List<Comment> findAll(){
        return em.createQuery("select m from Comment m", Comment.class)
                .getResultList();
    }

    // 특정 게시글의 모든 댓글 반환
    public List<Comment> findAllByPostId(Long postId){
        return em.createQuery("select m from Comment m where m.boardPost = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    // 특정 유저가 작성한 댓글 목록 반환
    public List<Comment> findAllByUserId(Long userId){
        return em.createQuery("select m from Comment m where m.user = :userId", Comment.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
