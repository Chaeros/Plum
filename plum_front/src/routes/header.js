import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

import {useNavigate} from 'react-router-dom'
import { useCookies } from 'react-cookie';
import axios from 'axios';

function Header() {
    let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신

    const [cookies, setCookie, removeCookie] = useCookies(['id']);
    const token = cookies.id; // 쿠키에서 id 를 꺼내기

    function Send(){
        axios.post('http://localhost:8080/sign-api/logout', {},
                 {
                headers: {
                    withCredentials:true,
                    Authorization: token
                }
            }
        )
        .then((response) => {
            navigate('/')
        })
        .catch((error) => {
            // 예외 처리
            console.log(error)
        })
    }

    function UpdateUser(){
        navigate('/updateUser')
    }

    const buttonColorStyle = {
        background: 'rgb(226,31,136)', 
        border:'rgb(226,31,136)'
      };

  return (      
    <header>
        <Navbar expand="lg" className="bg-body-tertiary" bg="light" data-bs-theme="light">
            <Container fluid>
                <Navbar.Brand  onClick={()=>{navigate('/community')}}><b>Plum</b></Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="me-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                        {/* <Nav.Link onClick={()=>{navigate('/announcement')}}>공지사항</Nav.Link> */}
                        {/* <Nav.Link onClick={()=>{navigate('/suggestions')}}>건의사항</Nav.Link> */}
                        {/* <NavDropdown title="Link" id="navbarScrollingDropdown">
                        <NavDropdown.Item href="#action3">Action</NavDropdown.Item>
                        <NavDropdown.Item href="#action4">
                            Another action
                        </NavDropdown.Item>
                        <NavDropdown.Divider />
                        <NavDropdown.Item href="#action5">
                            Something else here
                        </NavDropdown.Item>
                        </NavDropdown> */}
                    </Nav>

                    <Button style={buttonColorStyle} onClick={()=>{UpdateUser()}}>정보수정</Button>
                    <p>  </p>
                    <Button style={buttonColorStyle} onClick={()=>{Send()}}>로그아웃</Button>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    </header>
  );
}

export default Header;