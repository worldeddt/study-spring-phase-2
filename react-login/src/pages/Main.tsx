import {JSX} from "react";
import {useAuth} from "../components/AuthProvider.tsx";
import {useNavigate} from "react-router-dom";


export const Main = ():JSX.Element  => {
  const {isAuthenticated} = useAuth();
  const navigate = useNavigate();

  if (!isAuthenticated) {
    alert("로그인이 필요합니다.");
    navigate('/');
  }

  return (
    <div>
      <h1>date picker</h1>
      <h2>graph</h2>
      <button onClick={() => {
        navigate("/home")
      }}>home</button>
    </div>
  )
}