package com.springboot.plum.controller;

import com.springboot.plum.config.security.JwtTokenProvider;
import com.springboot.plum.data.dto.BoardPostDto;
import com.springboot.plum.data.entity.BoardPost;
import com.springboot.plum.data.entity.User;
import com.springboot.plum.data.form.BoardAddForm;
import com.springboot.plum.data.repository.BoardPostRepository;
import com.springboot.plum.service.BoardPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="*") // 이거 넣어야 CRos 에러 안남
@RequestMapping("user/boardPost")
@RequiredArgsConstructor
public class BoardPostController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardPostService boardPostService;

    @RequestMapping(value="/create", method= RequestMethod.POST)
    public Map<String,Object> AxiosFileTest (
        HttpServletRequest request,
        @RequestParam(value="file", required=false) List<MultipartFile> files) throws SQLException,IOException {

        BoardAddForm boardAddForm = new BoardAddForm(request.getParameter("title"),
                request.getParameter("content"),
                files,null);

        String token = jwtTokenProvider.resolveToken(request);
        UserDetails user = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto((User)user);
        BoardPost boardPost = boardPostService.post(boardPostDto);

        System.out.println(request.getParameter("category"));
        System.out.println(request.getParameter("title"));
        System.out.println(request.getParameter("content"));

        Map<String,Object> resultMap = new HashMap<String,Object>();
        String FileNames ="";
        System.out.println("paramMap =>"+files.get(0));

        String filepath = "D:/SpringProject/files/";
        for (MultipartFile mf : files) {

            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
            long fileSize = mf.getSize(); // 파일 사이즈
            System.out.println("originFileName : " + originFileName);
            System.out.println("fileSize : " + fileSize);

            String safeFile =System.currentTimeMillis() + originFileName;

            FileNames = FileNames+","+safeFile;
            try {
                File f1 = new File(filepath+safeFile);
                mf.transferTo(f1);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        FileNames = FileNames.substring(1);
        System.out.println("FileNames =>"+ FileNames);
        resultMap.put("JavaData", paramMap);
        return resultMap;
    }
}
