import {JSX, useEffect, useRef} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useAuth} from "../components/AuthProvider.tsx";


const Home = ():JSX.Element => {
  const {isAuthenticated} = useAuth();
  const navigate = useNavigate();
  const isCalled = useRef(false);
  const API_LOG_OUT = "http://localhost:8081/api/auth/logout"

  useEffect(() => {

    if (isCalled.current) return;
    isCalled.current = true;

    if (!isAuthenticated) {
      alert("로그인 필요");
      navigate("/")
    }

  }, []);

  const Logout = async () => {

    if (!isAuthenticated) {
      alert("유저 정보 누락");
      navigate("/");
    }

    const response = await axios.get(API_LOG_OUT, {withCredentials: true });

    if (!response) {
      alert("로그아웃 실패");
      return;
    }

    alert(`로그아웃 성공`)
    navigate("/");
  }

  return (
    <div>
      <h2>카카오 로그인 완료</h2>
      <button onClick={() => {
        navigate("/main")
      }}>main</button> <br/>
      <line>-</line><br/>
      <button onClick={Logout}>로그 아웃</button>
    </div>
  );
}


export default Home;