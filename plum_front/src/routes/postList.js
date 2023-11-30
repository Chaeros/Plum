import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom'
import axios from 'axios';
import { useCookies } from 'react-cookie';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { useDispatch, useSelector } from 'react-redux';
import { changeId } from './../store';
import '../css/pageButton.css';

function PostListCard() {
    let navigate = useNavigate(); // 페이지 이동 쉽게 가능 Link태그 대신
    const [cookies, setCookie, removeCookie] = useCookies(['id']);
    const token = cookies.id; // 쿠키에서 id 를 꺼내기

    let [num,setPageNum]=useState(1)
    let [categoryName,setCategoryName]=useState("자유게시판1")
    let data = {category: categoryName,pageNum: num}

    let [pageCount,setPageCount]=useState(1)

    let [tempKeyword,setTempKeyword] =useState("")
    let [keyword,setKeyword]=useState("")

    let [searchType, setSearchType] = useState('제목')

    const [boardList,setBoardList] = useState();

    let a = useSelector((state)=>{return state})
    const dispatch = useDispatch(); // useDispatch를 통해 dispatch 함수를 가져옴

    useEffect(() => {
      axios.get('http://localhost:8080/boardPost/boardsCount/'+ categoryName +"/"+ keyword, {
            params: {type: searchType},
            headers: {
              withCredentials:true,
              Authorization: token
          }
        })
        .then((response) => {
            setPageCount(response.data);
            console.log(response.data);
            console.log(pageCount);
        })
        .catch((error) => {
            // 예외 처리
        })

        axios.get('http://localhost:8080/boardPost/boardList/' + categoryName +"/1/" + keyword, {
          params: { type: searchType },
          headers: {
            withCredentials:true,
            Authorization: token
        }
        })
          .then((response) => {
            console.log(response);
            console.log(response.data);
            setBoardList(response.data);
            console.log(response.data[0]);
            console.log(boardList);
          })
          .catch((error) => {
            // 예외 처리
          });

      },[])

      useEffect(() => {
        // 검색 버튼이 클릭될 때 실행될 작업
        axios.get('http://localhost:8080/boardPost/boardList/' + categoryName +"/1/" + keyword, {
          params: { type: searchType },
          headers: {
            withCredentials:true,
            Authorization: token
        }
        })
          .then((response) => {
            console.log(response);
            console.log(response.data);
            setBoardList(response.data);
            console.log(response.data[0]);
            console.log(boardList);
          })
          .catch((error) => {
            // 예외 처리
          });

        axios.get('http://localhost:8080/boardPost/boardsCount/'+ categoryName +"/"+ keyword, {
            params: {type: searchType},
            headers: {
              withCredentials:true,
              Authorization: token
          }
        })
        .then((response) => {
            setPageCount(response.data);
            console.log(response.data);
            console.log("pageCount="+pageCount);
        })
        .catch((error) => {
            // 예외 처리
        })
      
        console.log('검색 유형:', searchType);
        console.log('키워드:', keyword);
      }, [searchType, keyword]);  // searchType 또는 keyword가 변경될 때마다 호출
      

      function SendAndNav(postId){

        let id ={post_id : postId}
        dispatch(changeId(postId))

        console.log(postId)
        console.log(a)
        navigate('/showAPost',{state:{id:postId}})
        
      }

      // let [arr,setArr] = useState([]);

      
        const [pageLinks, setPageLinks] = useState([]);

      
        useEffect(() => {
          const arr = [];
          for (let i = 0; i <= (pageCount-1)/10; ++i) {
            arr.push(
              <a
                key={`page${i+1}`}
                id={`page${i+1}`}
                onClick={() => handlePageClick(i+1)}
              >
                {i+1 + ' '}
              </a>
            );
          }
          setPageLinks(arr);
        }, [pageCount]); // pageCount가 변경될 때마다 실행
      
        const handlePageClick = (pageNumber) => {   
          axios
            .get(
              'http://localhost:8080/boardPost/boardList/' +
                categoryName +
                '/' +
                pageNumber +
                '/' +
                keyword,
              {
                params: { type: searchType },
                headers: {
                  withCredentials:true,
                  Authorization: token
                }
              }
            )
            .then((response) => {
              console.log(response);
              console.log(response.data);
              setBoardList(response.data);
              console.log(response.data[0]);
              console.log(boardList);
            })
            .catch((error) => {
              // 예외 처리
            });
        };
    
    
      const handleKeywordChange = (event) => {
        setTempKeyword(event.target.value);
      };
    
      const handleSearchButtonClick = () => {
        // 이곳에서 검색 버튼이 클릭될 때 실행할 작업을 수행하면 됩니다.
        setSearchType(document.getElementById('type').value)
        setKeyword(tempKeyword);

        console.log('검색 유형:', searchType);
        console.log('키워드:', keyword);
      };
      
      const containerStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        // justifyContent: 'center', // 수직 정렬
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
      <div style={containerStyle} >
        <section class="ftco-section">
        <h1><b>게시판</b></h1>
          <br></br>
          <div class="container">
            <div class="row">
              <div class="col-md-12">
                <div class="table-wrap">
                  <table class="table" style={tableStyle}>
                    <thead class="thead-dark">
                      <tr>
                        <th>No.</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                        {/* <th>&nbsp;</th> */}
                      </tr>
                    </thead>
                    <tbody>

                      {boardList && boardList.map((board) =>(

                        <tr class="alert" role="alert">
                        <th scope="row">{board.id}</th>
                        <td><a onClick={()=>SendAndNav(board.id)}>{board.title}</a></td>
                        <td>{board.writer}</td>
                        <td>{board.writeDate}</td>
                        <td>{board.views}</td>
                        {/* <td>
                          <a href="#" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true"><i class="fa fa-close"></i></span>
                          </a>
                        </td> */}
                        </tr>
                      ))}
                      
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </section>

        <div>{pageLinks}</div>
        
        <Form className="d-flex">
            
          <Form.Select id="type" style={{ width: '297px', height: '50px'}} aria-label="Default select example">
              <option>제목</option>
              <option>작성자</option>              
          </Form.Select>
            <Form.Control style={{ width: '700px', height: '50px'}}
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
              controlId="searchVal"
              value={tempKeyword}
              onChange={handleKeywordChange}
            />
            <Button variant="outline-success" 
            onClick={handleSearchButtonClick} style={{ width: '100px', height: '50px'}}>검색</Button>
        </Form>
        <br></br>
        <button onClick={()=>{navigate('/postwrite')}} 
        style={Object.assign({},{ width: '200px', height: '50px'}, buttonColorStyle)}
        class="btn write-button btn-primary">글쓰기</button>

        <script src="js/jquery.min.js"></script>
  <script src="js/popper.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
      </div>
    );
  }

  export default PostListCard;