import Button from 'react-bootstrap/Button';
import axios from 'axios';
import { useCookies } from 'react-cookie';

function AnnouncementCard() {

  const [cookies, setCookie, removeCookie] = useCookies(['id']);

  return (
    <>
      <header className="App-header">
        <h1>공지사항</h1>
      지금은 1도 없습니다.^^
      </header>
      <Button variant="primary"
      onClick={()=>{
        const token = cookies.id; // 쿠키에서 id 를 꺼내기
        console.log(token);
        let data={data1:"aa", data2:"bb"}
        axios.get('http://localhost:8080/noticeboard',
          {headers:{
            withCredentials:true,
            Authorization: token
          }}
        ).then((response,error)=> {
          console.log(response.data);
          console.log(error);
          console.log(response.data[0]);
        });
      }}>Send</Button>{' '}

      <Button variant="primary"
      onClick={()=>{
        const token = cookies.id; // 쿠키에서 id 를 꺼내기
        console.log(token);
        const url = 'http://localhost:8080/user/hello4'
        let data={data1:"aa", data2:"bb"}
        axios.post(url,
          JSON.stringify(data),
          {headers:{
            'Content-Type': 'application/json',
            withCredentials:true,
            Authorization: token
          }}
        ).then((response,error)=> {
          console.log(response);
          console.log(error);
          console.log(response.data[0]);
        });
      }}>Send</Button>{' '}

    </>
  );
}

export default AnnouncementCard;