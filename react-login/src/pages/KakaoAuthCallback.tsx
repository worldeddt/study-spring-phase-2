import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const KakaoAuthCallback = () => {
  const navigate = useNavigate();
  const isCalled = useRef(false);
  const auth_url = "http://localhost:8081/api/auth/kakao";
  const API_LOG_OUT = "https://kapi.kakao.com/v1/user/logout"

  useEffect(() => {
    if (isCalled.current) return;
    isCalled.current = true;

    const getToken = async () => {
      const code = new URL(window.location.href).searchParams.get("code");

      if (!code) {
        console.error("로그인 실패");
        return;
      }

      try {
        const response = await axios.post(
          auth_url, {code},
          { withCredentials: true}
        );

        console.log("로그인 성공:", response.data);
        navigate("/home");
      } catch (e) {
        console.error("로그인 실패 : ", e);
      }
    };

    getToken();
  }, []);


  const cancelLogin = async () => {

    if (!localStorage.getItem("user_token")) {
      navigate("/");
    } else {
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
  }

  return (
    <div>
      <h2>카카오 로그인 처리 중...</h2>
      <button onClick={cancelLogin}> 로그인 취소</button>
    </div>
  );
};

export default KakaoAuthCallback;
