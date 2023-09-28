package com.springboot.plum.repository;

import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.NoticeBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
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

    // 페이지 당 10개의 게시글 반환
    public List<BoardPost> findOneBoardPostListPage(NoticeBoard noticeBoard, int pageNum){
        return em.createQuery("select m from BoardPost m where m.noticeBoard = :noticeBoard order by m.id desc", BoardPost.class)
                .setParameter("noticeBoard", noticeBoard)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                .setFirstResult(10*(pageNum-1))
                .setMaxResults(10*pageNum-1)
                .getResultList();
    }

    // 해당 카테고리 게시판의 총 게시글 개수 반환
    public long boardCount(NoticeBoard noticeBoard){
        return em.createQuery("select count(m) from BoardPost m where m.noticeBoard = :noticeBoard",Long.class)
                .setParameter("noticeBoard", noticeBoard)
                .getSingleResult();
    }

    // 해당 카테고리 게시판 검색어에 알맞는 게시글 개수 반환
    public long boardCountByKeyword(NoticeBoard noticeBoard, String keyword, String type){
        if(type.equals("제목")) {
            return em.createQuery("select count(m) from BoardPost m where m.noticeBoard = :noticeBoard " +
                            "and m.title like :keyword", Long.class)
                    .setParameter("noticeBoard", noticeBoard)
                    .setParameter("keyword","%"+keyword+"%")
                    .getSingleResult();
        }
        else if(type.equals("작성자")) {
            return em.createQuery("select count(m) from BoardPost m where m.noticeBoard = :noticeBoard " +
                            "and m.writer like :keyword", Long.class)
                    .setParameter("noticeBoard", noticeBoard)
                    .setParameter("keyword","%"+keyword+"%")
                    .getSingleResult();
        }
        return 0;
    }

    public List<BoardPost> findBoardsByKeword(NoticeBoard noticeBoard, String keyword, String type, int pageNum) {
        if(type.equals("제목")){
            log.info("findBoardsByKeword 제목");
            return em.createQuery("select m from BoardPost m where m.noticeBoard = :noticeBoard and m.title like :keyword" +
                            " order by m.id desc", BoardPost.class)
                    .setParameter("noticeBoard", noticeBoard)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                    .setParameter("keyword","%"+keyword+"%")
                    .setFirstResult(10*(pageNum-1))
                    .setMaxResults(10*pageNum-1)
                    .getResultList();
        }
        else if(type.equals("작성자")){
            log.info("findBoardsByKeword 작성자");
            return em.createQuery("select m from BoardPost m where m.noticeBoard = :noticeBoard and m.writer like :keyword" +
                            " order by m.id desc", BoardPost.class)
                    .setParameter("noticeBoard", noticeBoard)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                    .setParameter("keyword","%"+keyword+"%")
                    .setFirstResult(10*(pageNum-1))
                    .setMaxResults(10*pageNum-1)
                    .getResultList();
        }

        return null;
    }

    // 게시글 조회수 증가
    @Transactional
    public void increaseViews(Long id){
        log.info("{}번 게시글 조회수 증가",id);
        BoardPost boardPost = em.find(BoardPost.class, id);
        boardPost.setViews(boardPost.getViews()+1);
    }
}