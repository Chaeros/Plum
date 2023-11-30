import { useLocation } from "react-router";
import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';
import { useCookies } from 'react-cookie';

import Form from 'react-bootstrap/Form';

function ShowAPosttCard() {
    let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신
    
    const location = useLocation();
    const postId = location.state.id;

    let [post,setPost]=useState()
    let [images,setImages]=useState()
    let [comments,setComments]=useState()

    // 댓글 입력시 변경 감지하여 post 내용을 다시 받아오게 하기위해 사용
    let [commentAdd,setCommentAdd]=useState(0)

    const [cookies, setCookie, removeCookie] = useCookies(['id']);
    const token = cookies.id; // 쿠키에서 id 를 꺼내기

    let [isWriter, setIsWriter] =useState(false);
    let [commenter, setCommenter] = useState("");
    let [deleteCount, setDeleteCount]=useState(0);
    

    const fd = new FormData();
    fd.append("post_id",postId);
    useEffect(() => {
        console.log('api 호출')

        console.log(postId);
        axios.post('http://localhost:8080/boardPost/isWriter',
        fd,
          {headers:{
            "Content-Type": `multipart/form-data; `,
            withCredentials:true,
            Authorization: token
          }}
        ).then((response,error)=> {
          console.log(response.data);
          setIsWriter(response.data);

            axios.post('http://localhost:8080/boardPost/postViewsUp/'+postId,
            {}, {headers:{
                withCredentials:true,
                Authorization: token
            }}).catch((error) => {
                // 예외 처리
                console.log(error)
                if(error.response.status==401){
                    navigate('/')
                }
            });

            axios.get('http://localhost:8080/sign-api/whoareyou',
             {headers:{
                withCredentials:true,
                Authorization: token
            }}).then((response,error)=> {
                console.log(response)
                setCommenter(response.data);
            }).catch((error) => {
                // 예외 처리
                console.log(error)
                if(error.response.status==401){
                    navigate('/')
                }
            });

        }).catch((error) => {
            // 예외 처리
            console.log(error)
            if(error.response.status==401){
                navigate('/')
            }
        });
    },[])

    function Send(postId){
        let id ={post_id : postId};
        console.log(id);

        axios.get('http://localhost:8080/boardPost/'+postId, 
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
            setImages(response.data.imagesURL)
            setComments(response.data.comments)
            console.log(response.data.comments)
        })
        .catch((error) => {
            // 예외 처리
            console.log(error)
            if(error.response.status==401){
                navigate('/')
                alert("세션이 만료되어 강제 로그아웃됩니다. 다시 로그인 해주세요.")
            }
        })
    }

    useEffect(() => {
        Send(postId)
    },[commentAdd,deleteCount])

    function AddComment(){

        let data ={content: document.getElementById('content').value,
                    post_id : postId};
        
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
            setCommentAdd(commentAdd+1)
        })
        .catch((error) => {
            // 예외 처리
            if(error.response.status==401){
                navigate('/')
                alert("세션이 만료되어 강제 로그아웃됩니다. 다시 로그인 해주세요.")
            }
        })
    }

    // const fd2 = new FormData();
    // fd2.append("post_id",postId);
    function DeletePost(){

        // let data ={post_id : postId};
        axios.delete('http://localhost:8080/boardPost/'+postId, 
        {
            headers: {
                withCredentials:true,
                Authorization: token
            }
        })
        .then((response) => {
            navigate('/community')
        })
        .catch((error) => {
            // 예외 처리
            if(error.response.status==401){
                navigate('/')
                alert("세션이 만료되어 강제 로그아웃됩니다. 다시 로그인 해주세요.")
            }
        })
    }

    function UpdatePost(){        
        navigate('/postUpdate', {state:{id:postId}})
    }

    function DeleteCommentButtonImpl(commentId){
        axios.delete('http://localhost:8080/comment/'+commentId, 
        {
            headers: {
                withCredentials:true,
                Authorization: token
            }
        }).then((response) => {
            setDeleteCount(deleteCount+1);
        }).catch((error) => {
            // 예외 처리
            if(error.response.status==401){
                navigate('/')
                alert("세션이 만료되어 강제 로그아웃됩니다. 다시 로그인 해주세요.")
            }
        })
    }

    function DeletePostButton(){
        if(isWriter){ return (<button style={buttonColorStyle} onClick={()=> DeletePost()} class="btn write-button btn-primary">게시글 삭제</button>)}
    }
    function UpdatePostButton(){
        if(isWriter){ return (<button style={buttonColorStyle} onClick={()=> UpdatePost()} class="btn write-button btn-primary">게시글 수정</button>)}
    }

    function DeleteCommentButton(commenterName,commentId){
        if(commenter==commenterName){return (<button onClick={()=> DeleteCommentButtonImpl(commentId)} >X</button>)}
    }

    const containerStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'start',
        // justifyContent: 'center', // 수직 정렬
        marginLeft: '400px', // 왼쪽으로부터 500px 떨어지게 만듭니다.
        minHeight: '100vh', // 화면 높이에 따라 가운데 정렬을 위한 스타일
      };
    
      const buttonContainerStyle = {
        display: 'flex',
        justifyContent: 'center',
        gap: '10px', // 버튼 사이의 간격
      };

      const tableStyle = {
        width: '1200px', // 테이블의 폭을 조정합니다.
        // maxWidth: '800px', // 테이블의 최대 폭을 설정합니다.
      };

      const buttonColorStyle = {
        background: 'rgb(226,31,136)', 
        border:'rgb(226,31,136)'
      };

    return (
        <div>
        <div style={containerStyle}>
        <h1><b>{post && post.title}  {UpdatePostButton()}{DeletePostButton()}</b></h1>
        <p>{post && post.writer} {post && post.writeDate} {post &&post.noticeBoardName}</p>
        <hr style={{ width: '700px', height: '1px'}}></hr>
        <p>{post && post.content}</p>

        {images && images.map((item) => {
          console.log(process.env.REACT_APP_API_URL+item);
            return (
                
                <div key={item}>
                    <img
                        src={process.env.REACT_APP_API_URL+item}
                        alt={"img"}
                        style={{width:"200px", height:"150px"}}
                    />
                </div>
            )
        })}

        <br></br><br></br>
        <hr style={{ width: '700px', height: '1px'}}></hr>
        <p><b>[작성된 댓글]</b></p>
        {comments && comments.map((item,index) => {
          console.log(process.env.REACT_APP_API_URL+item);
            return (
                <div key={item+index}>
                    {/* <div><hr style={{ width: '700px', height: '1px'}}></hr></div> */}
                    <div style={{marginLeft :'1px', alignItems: 'start'}}>{item.writer_user.name} : {item.content}  {item.commentDate} {DeleteCommentButton(item.writer_user.uid,item.comment_id)}</div> 
                </div>
            )
        })}

        <br></br>
        <Form.Floating className="mb-3" style={{ width: '700px', height: '50px'}}>
            <Form.Control
            id="content"
            type="text"
            />
            <label htmlFor="floatingInputCustom">댓글내용</label>
        </Form.Floating>
        <button 
        style={Object.assign({},{ marginLeft:'610px'}, buttonColorStyle)}
        onClick={()=> AddComment()} class="btn write-button btn-primary">댓글등록</button>
    </div>
    </div>
    );
  }

export default ShowAPosttCard;