import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";


const KakaoAuthCallback = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const getToken = async () => {
      const code = new URL(window.location.href).searchParams.get("code");

      if (!code) {
        console.error("로그인 실패");
        return;
      }

      try {
        //request token
        const response = await axios.post(
          "https://kauth.kakao.com/oauth/token",
          new URLSearchParams({
            grant_type: "authorization_code",
            client_id: import.meta.env.VITE_KAKAO_REST_API_KEY,
            redirect_uri: import.meta.env.VITE_KAKAO_REDIRECT_URI,
            code
          }),
          {headers: {"Content-Type": "application/x-www-form-urlencoded"}}
        );

        console.log(response);
        const {access_token} = response.data;

        const userInfo = await axios.get("https://kapi.kakao.com/v2/user/me", {
          headers: {Authorization: `Bearer ${access_token}`},
        });

        if (!userInfo.data && !userInfo.data.id) {
          alert("로그인 실패");
          console.log("로그인 아이디 누락");
          navigate("/");
        }

        localStorage.setItem("user_info", JSON.stringify(userInfo.data));
        localStorage.setItem("user_token", access_token);
        navigate("/home")
      } catch (e) {
        console.error("로그인 실패 : ", e);
      }
    }

    getToken();
  }, []);

  return <h2>카카오 로그인 처리 중...</h2>;
}



export default KakaoAuthCallback;