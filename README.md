# 웹 게시판
스프링과 리액트를 사용하여 개발한 게시판입니다.

# 제작 배경
환자들 간 소통을 할 수 있는 커뮤니티 사이트 제작을 위해 만든 게시판 서비스입니다.

<h1>개요</h1>
프로젝트 명칭 : Plum<br>
개발 인원 : 1명<br>
개발 기간 : 2023.08.01 ~ 2023.10.08<br>
주요 기능 : <br>
   1. 게시판 - CRUD 기능, 조회수, 페이징 및 검색 처리<br>
   2. 사용자 - jwt를 통한 Security 회원가입 및 로그인/아웃, Redis 인메모리를 통한 로그인 관리<br>
   3. 댓글 - CRUD 기능<br>
개발 언어 : Java 11<br>
개발 환경 : SpringBoot 2.7.14, maven, JPA, Spring Security, Redis, React<br>
데이터베이스 : MySQL<br>
형상관리 툴 : GitHub<br>

# 기능
- Spring Security의 jwt와 redis를 통한 로그인/로그아웃
- 회원 가입
- 게시글 생성
   - 다중 이미지 삽입 가능
   - 작성한 사용자에 한해서 수정 및 삭제 가능
   - 댓글 작성
        - 작성한 사용자에 한해서 삭제 가능
- 게시판 기능
   - 제목/작성자 입력을 통한 특정 게시글 검색 기능
   - 페이징 기능
   - 조회수 출력 기능


<h1>DB 설계</h1>

![image](https://github.com/Chaeros/Plum/assets/91451735/c005c844-c453-4b9b-9c12-9a0e96a5f635)

![image](https://github.com/Chaeros/Plum/assets/91451735/bf050d8b-4fce-4923-9040-bae9e82ff1a7)

![image](https://github.com/Chaeros/Plum/assets/91451735/9bab9244-54df-4ec7-a388-469dbd5b8028)

![image](https://github.com/Chaeros/Plum/assets/91451735/9e4d2f71-da6b-4316-9bb2-ea9c87d16800)

![image](https://github.com/Chaeros/Plum/assets/91451735/e13f81ca-0725-4b41-89b6-483de8b6b2bf)

![image](https://github.com/Chaeros/Plum/assets/91451735/6bb226c7-2708-40c8-8d21-ae63d67a3c52)

![image](https://github.com/Chaeros/Plum/assets/91451735/b757cb90-1f56-4bf9-b4ef-3d82e2fb3088)

<br><br><br>


<h1>API 설계</h1>

![image](https://github.com/Chaeros/Plum/assets/91451735/27567b3c-d696-497e-81b5-c079e2a15b13)

![image](https://github.com/Chaeros/Plum/assets/91451735/44bc1980-b706-40f1-88b7-d13b88727d15)

![image](https://github.com/Chaeros/Plum/assets/91451735/327d8b58-d079-48ca-8f87-78bf7a6c342d)

## 실행 이미지
<b>로그인 화면</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/064e75e0-0246-4382-965f-305b7c804bda)
<br></br>
- JWT 토큰을 이용하여 로그인 상태 유지
- Redis 인메모리 DB를 사용해 토큰 상태 저장

<b>회원가입 화면</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/99d8c574-c778-42f4-a622-f9f40f8d6894)
<br></br>
- 중복되는 아이디가 존재하면 경고창 출력
- 공백 입력이 있으면 경고창 출력

<b>게시판 화면</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/06266ce4-ad2a-42e4-8339-989a87e7c906)
<br></br>
- 게시글 클릭 시, 조회수 1 증가
- 키워드 검색을 통한 특정 게시글 목록 출력
- 페이징 기능(한 페이지 당 10개의 게시글 출력)
- 우측 상단의 버튼을 통해 로그아웃 가능

<b>키워드 검색</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/59703d7f-b117-4da0-8b8f-089e9c488959)
<br></br>
- 제목/작성자 카테고리 선택 가능
- 빈칸 입력 시, 모든 게시글을 불러옴

<b>글쓰기 화면</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/1dac2cff-bb9c-4e79-9022-588a414c2050)
<br></br>
- 다중 이미지 업로드 가능
- 게시판 선택 가능

<b>게시글 화면</b>
![image](https://github.com/Chaeros/Plum/assets/91451735/e990e6f2-339a-4c29-8517-4337fc68acf1)
<br></br>
- 자신이 작성한 게시글의 우측 상단에 게시글 수정/삭제 버튼 출력됨
- 댓글 기능
- 자신이 작성한 댓글의 우측에 댓글 삭제 버튼 출력됨
