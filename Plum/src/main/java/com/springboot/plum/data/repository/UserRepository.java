package com.springboot.plum.data.repository;

import com.springboot.plum.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 예제 13.7
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUid(String uid);

}