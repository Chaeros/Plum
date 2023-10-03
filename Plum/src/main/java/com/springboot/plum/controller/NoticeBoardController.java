package com.springboot.plum.controller;

import com.springboot.plum.data.dto.NoticeBoardDto;
import com.springboot.plum.data.entity.NoticeBoard;
import com.springboot.plum.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/noticeboard")
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;
    @GetMapping()
    public List<NoticeBoardDto> readNoticeBoardList(){
        List<NoticeBoard> noticeBoards = noticeBoardService.findMembers();
        List<NoticeBoardDto> boardDtos = new ArrayList<>();

        for(NoticeBoard noticeBoard:noticeBoards){
            boardDtos.add(new NoticeBoardDto(noticeBoard.getName()));
        }
        return boardDtos;
    }
}
