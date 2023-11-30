import FloatingLabel from 'react-bootstrap/FloatingLabel';
import Form from 'react-bootstrap/Form';
import {useNavigate} from 'react-router-dom'
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useLocation } from "react-router";

function UpdateUserCard() {
    let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신
    const [cookies, setCookie, removeCookie] = useCookies(['id']);
    const token = cookies.id; // 쿠키에서 id 를 꺼내기
    let data={data1:"aa", data2:"bb"}

    const location = useLocation();

    let [boardNames,setboardNames]=useState('')
    let temp;

    let [post,setPost]=useState("")

    useEffect(() => {
        
        axios.get('http://localhost:8080/user/', 
                 {
                headers: {
                    withCredentials:true,
                    Authorization: token
                }
            }
        )
        .then((response) => {
            console.log(response)
            setPost(response.data)
        })
        .catch((error) => {
            // 예외 처리
            if(error.response.status==401){
              navigate('/')
              alert("세션이 만료되어 강제 로그아웃됩니다. 다시 로그인 해주세요.")
          }
        })

    },[])

    let [mainImg,setMainImg] = useState("");
    const setPreviewImg = (event) => {
        var reader = new FileReader();

          reader.onload = function(event) {
            setMainImg(event.target.result);
        };

        if(event.target.files[0]){  // 이 조건문이 있어야 파일 업로드 다이얼로그에서 x버튼을 눌러 탈출해도 에러가 발생하지 않음
          reader.readAsDataURL(event.target.files[0]);
        }
    }


    const [files, setFiles] = useState([]);	//파일

    const handleChangeFile = (event) => {

      console.log(event.target.files);
      if(event.target.files.length!=0){  // 이 조건문으로 인해 파일 업로드시 이미지를 선택하지 않아도 기존의 업로드할 내역들이 유지됨
        setFiles(Array.from(event.target.files || []));
      }
    }

    const handleDelete = (index) =>{
      setFiles([...files.slice(0,index), ... files.slice(index+1)])
    };

    const containerStyle = {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      // justifyContent: 'center', // 수직 정렬
      minHeight: '100vh', // 화면 높이에 따라 가운데 정렬을 위한 스타일
    };

    const sizeStyle ={
       width: '900px',
       height: '50px'
    }

    const buttonColorStyle = {
      background: 'rgb(226,31,136)', 
      border:'rgb(226,31,136)'
    };


    return (
      <div style={containerStyle} >
        <h1><b>내 정보</b></h1>
        
        <p></p>


          <FloatingLabel
          style={sizeStyle}
            controlId="title"
            label="제목"
            className="mb-3"
          >
            <Form.Control as="textarea" placeholder="Leave a comment here" defaultValue={post.title}/>
          </FloatingLabel>
          <FloatingLabel style={sizeStyle} controlId="content" label="내용">
            <Form.Control
              as="textarea"
              placeholder="Leave a comment here"
              style={{ height: '100px' }}
              defaultValue={post.content}
            />
          </FloatingLabel>

          <Form.Group controlId="formFileMultiple" className="mb-3">
            <Form.Label>수정의 경우 이미지를 변경할 수 없습니다.</Form.Label>
            {/* <Form.Label>여러개의 이미지를 삽입하려면 드래그 및 컨트롤 클릭으로 다중 선택해주세요!</Form.Label> */}
            {/* <Form.Control type="file" accept=".jpg,.png,.jpeg" onChange={handleChangeFile} multiple val={images}/> */}
          </Form.Group>

          <div>
          <button 
            style={Object.assign({},{ width: '200px', height: '50px'}, buttonColorStyle)}
            // onClick={()=> Send()}
             class="btn write-button btn-primary">수정하기</button>
          </div>
          
        </div>
      );
  }
  
  export default UpdateUserCard;