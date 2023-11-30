import Button from 'react-bootstrap/Button';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useState, useEffect  } from 'react';
import Form from 'react-bootstrap/Form';

function SuggenstionsCard() {

  const [cookies, setCookie, removeCookie] = useCookies(['id']);

  const fd = new FormData();
  fd.append("post_id","6");

  let [images,setImages] = useState([]);
  const token = cookies.id; // 쿠키에서 id 를 꺼내기

  // useEffect(() => {
  //   if (images.length > 0) {
  //     console.log(images);
  //   }
  // }, [images]);

  function Send(){

    let data ={content: document.getElementById('content').value,
              post_id : 6};
    
    console.log(data);
   
    axios.post('http://localhost:8080/comment', 
    JSON.stringify(data), {
      headers: {
        'Content-Type': 'application/json',
        withCredentials:true,
        Authorization: token
      }
    })
    .then((response) => {
      console.log(response);
      
    })
    .catch((error) => {
      // 예외 처리
    })
  
  }

  return (
    <>
      <header className="App-header">
        <h1>건의사항</h1>
      지금은 1도 없습니다.^^
      </header>
      <Form.Floating className="mb-3">
        <Form.Control
          id="content"
          type="text"
          placeholder="text"
        />
        <label htmlFor="floatingInputCustom">댓글내용</label>
      </Form.Floating>
      <button onClick={()=> Send()} class="btn write-button btn-primary">댓글등록</button>
      <div/>
      <div/>

      <div>
        {images && images.map((item) => {
          console.log(process.env.REACT_APP_API_URL+item);
            return (
                <div key={item}>
                    <img
                        src={process.env.REACT_APP_API_URL+item}
                        alt={"img"}
                        style={{width:"200px", height:"150px"}}
                    />
                    {/* <button onClick={() => downloadImage(item.filename)}>다운로드</button> */}
                </div>
            )
        })}
      </div>

        <div>
            <img  alt="test" src="http://localhost:8080/images/6bba913e-a85f-4f83-80f3-fe4538278dc6.PNG"
             width="100px" height="150px"></img>
            <img  alt="멍멍" src="http://localhost:8080/images/dog3.jpg"
             width="100px" height="150px"></img>
        </div>
    </>
  );
}

export default SuggenstionsCard;