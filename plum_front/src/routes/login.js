import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import axios from 'axios';
import { useCookies } from 'react-cookie'; // useCookies import
import {useNavigate} from 'react-router-dom'

function LoginCard() {
  let navigate = useNavigate();
  const [cookies, setCookie] = useCookies(['id']);

  const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '100vh', // 화면 높이에 따라 가운데 정렬을 위한 스타일
  };

  const buttonContainerStyle = {
    display: 'flex',
    justifyContent: 'center',
    gap: '10px', // 버튼 사이의 간격
  };

  const buttonColorStyle = {
    background: 'rgb(226,31,136)', 
    border:'rgb(226,31,136)'
  };

  return (
    <div style={containerStyle}>
      <h1><b>Plum 로그인</b></h1>
      <br></br>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="id"
          type="text"
          placeholder="text"
        />
        <label htmlFor="floatingInputCustom">아이디</label>
      </Form.Floating>
      <Form.Floating style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="password"
          type="password"
          placeholder="Password"
        />
        <label htmlFor="floatingPasswordCustom">비밀번호</label>
      </Form.Floating>
      <br></br>

      <div style={buttonContainerStyle}>
        <Button variant="primary" style={{ width: '297px', height: '50px',background: 'rgb(226,31,136)', border:'rgb(226,31,136)'}}
          onClick={()=>{
              axios(
                {
                  url: '/sign-api/sign-in',
                  method: 'post',
                  data: 
                    {userid: document.getElementById('id').value,
                    password: document.getElementById('password').value}, 
                  baseURL: 'http://localhost:8080'
                }
              ).then(function (response) {
                console.log(response)
                console.log(response.data.success)
                if (response.data.token) {
                  setCookie('id', response.data.token);
                }
                if(response.data.success==true){
                  navigate('community')
                }
              }).catch((error) => {
                alert(" 아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.입력하신 내용을 다시 확인해주세요.");
            });
          }}>로그인</Button>

        <Button style={Object.assign({},{ width: '297px', height: '50px'}, buttonColorStyle)} onClick={()=>{navigate('signup')}}>회원가입</Button>
      </div>
    </div>
  );
}

export default LoginCard;