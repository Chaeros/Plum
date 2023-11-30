import { Cookies } from "react-cookie";
import { useCookies } from 'react-cookie';
import {useNavigate} from 'react-router-dom'
import Button from 'react-bootstrap/Button';

function SectionHeader(props){
    let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신
    const [cookies, setCookie, removeCookie] = useCookies(['id']);

    return (
        <>
            <h1>게시판</h1>
            <button onClick={()=>{navigate('/postwrite')}} class="btn write-button btn-primary">글쓰기</button>
        </>
    )
}

export default SectionHeader;