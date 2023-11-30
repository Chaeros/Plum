package com.springboot.plum.repository;

import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NormalUserRepository {
    private final EntityManager em;

    public User save(User boardPost){ em.persist(boardPost); return boardPost; }
    public User findOne(Long id){ return em.find(User.class,id); }
    public void delete(Long id){ em.remove(findOne(id)); }
    public void updatePassword(Long id,String password){
        User user = em.find(User.class,id);
        user.setPassword(password);
    }

    public User findOneByID(String uid){
        return em.createQuery("select m from User m where m.uid = :uid", User.class)
                .setParameter("uid", uid)  // 파라미터 바인딩, where에 ':파라미터명' 으로 사용
                .getSingleResult();
    }
}
