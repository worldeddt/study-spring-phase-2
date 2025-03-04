import {JSX, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";


const Home = ():JSX.Element => {
  const navigate = useNavigate();
  const API_LOG_OUT = "https://kapi.kakao.com/v1/user/logout"

  useEffect(() => {
    const item = localStorage.getItem("user_info");

    if (!item) {
      alert("로그인 필요");
      navigate("/")
    }

  }, []);

  const Logout = async () => {

    if (!localStorage.getItem("user_token")) {
      alert("유저 정보 누락");
      navigate("/");
    }

    const response = await axios.post(API_LOG_OUT, {},
      {
        headers:
          {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            "Authorization": ` Bearer ${localStorage.getItem("user_token")}`
          }
      }
    );

    if (!response) {
      alert("로그아웃 실패");
      return;
    }

    localStorage.removeItem("user_info");
    localStorage.removeItem("user_token");

    navigate("/");
  }

  return (
    <div>
      <h2>카카오 로그인 완료</h2>
      <button onClick={Logout}>로그 아웃</button>
    </div>
  );
}


export default Home;