import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import axios from 'axios';
import {useNavigate} from 'react-router-dom'

function SignUpCard() {
  let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신

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
      <h1><b>Plum 회원가입</b></h1>
      <br></br>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="id"
          type="text"
          placeholder="id"
        />
        <label htmlFor="floatingInputCustom">아이디</label>
      </Form.Floating>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="password"
          type="password"
          placeholder="Password"
        />
        <label htmlFor="floatingPasswordCustom">비밀번호</label>
      </Form.Floating>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="confirmPassword"
          type="password"
          placeholder="Password"
        />
        <label htmlFor="floatingPasswordCustom">비밀번호 확인</label>
      </Form.Floating>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="name"
          type="text"
          placeholder="text"
        />
        <label htmlFor="floatingPasswordCustom">이름</label>
      </Form.Floating>
      <Form.Floating className="mb-3" style={{ width: '600px', height: '50px', textAlign: 'center' }}>
        <Form.Control
          id="phoneNumber"
          type="text"
          placeholder="text"
        />
        <label htmlFor="floatingPasswordCustom">휴대폰번호</label>
      </Form.Floating>
      <Button variant="primary" style={Object.assign({},{ width: '600px', height: '50px', textAlign: 'center'}, buttonColorStyle)}
        onClick={()=>{
          const password1=document.getElementById('password').value;
          const password2=document.getElementById('confirmPassword').value;
          (password1===password2)?
          axios(
            {
              url: '/sign-api/sign-up',
              method: 'post',
              data: 
                {id: document.getElementById('id').value,
                password: document.getElementById('password').value,
                name: document.getElementById('name').value,
                phoneNumber: document.getElementById('phoneNumber').value}, 
              baseURL: 'http://localhost:8080'
            }
          ).then(function (response) {
            navigate('/')
          }
          ).catch((error) => {
            alert("이미 존재하는 아이디입니다. 수정해주세요.");
        }):alert("비밀번호가 일치하지 않습니다.");
        }}>회원가입</Button>{' '}
    </div>
  );
}

export default SignUpCard;