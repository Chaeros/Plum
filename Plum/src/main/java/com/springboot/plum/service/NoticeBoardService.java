package com.springboot.plum.service;

import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.data.repository.NoticeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)   // readOnly=true 옵션을 주면 JPA가 조회하는 곳에서 성능을 더 최적화한다. 단, 쓰기 불가
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;  // 컴파일 시점에서 생성자를 통해 주입을 해주는지 체크를 해줄수 있기때문에 final 쓰면 좋다.

    @Autowired
    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public List<NoticeBoard> findMembers(){
        return noticeBoardRepository.findAll();
    }

    public NoticeBoard findOne(Long noticeId){
        return noticeBoardRepository.findOne(noticeId);
    }
}
