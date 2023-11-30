import logo from './logo.svg';
import './App.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';

import LoginCard from './routes/login';
import SignUpCard from './routes/signUp';
import Header from './routes/header';
// import SectionHeader from './route/sectionHeader';
import PostListCard from './routes/postList';
import PostCard from './routes/postWrite';
import ShowAPosttCard from './routes/showAPost';
import AnnouncementCard from './routes/announcement';
import SuggenstionsCard from './routes/suggestions';
import UpdatePostCard from './routes/updatePost.js';
import UpdateUserCard from './routes/user_update.js';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<><LoginCard/></>}/>

        {/* 회원가입 관련 */}
        <Route path="signup" element={<><SignUpCard/></>}/>

        {/* 게시판 관련 */}
        <Route path="community" element={<><Header/><PostListCard/></>}/>
        <Route path="postwrite" element={<><Header/><PostCard/></>}/>
        <Route path="postUpdate" element={<><Header/><UpdatePostCard/></>}/>

        {/* 특정 게시물로 이동 */}
        <Route path="showAPost" element={<><Header/><ShowAPosttCard/></>}/>

        {/* 공지사항, 건의사항 */}
        <Route path="announcement" element={<><Header/><AnnouncementCard/><PostListCard/></>}/>
        <Route path="suggestions" element={<><Header/><SuggenstionsCard/></>}/>

        {/* 유저 정보 관리 */}
        <Route path="updateUser" element={<><Header/><UpdateUserCard/></>}/>

      </Routes>
    </div>
  );
}

export default App;
