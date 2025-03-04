import './App.css'
import {BrowserRouter as Router, useRoutes} from "react-router-dom";
import KakaoLoginButton from "./pages/KakaoLoginButton.tsx";
import KakaoAuthCallback from "./pages/KakaoAuthCallback.tsx";
import Home from "./pages/Home.tsx";

const AppRoutes = () => {
  return useRoutes([
    { path: "/home", element: <Home/> },
    { path: "/", element: <KakaoLoginButton/> },
    { path: "/auth/kakao/callback", element: <KakaoAuthCallback/> },
  ]);
};

function App() {


  return (
    <Router>
      <AppRoutes/>
    </Router>
  )
}

export default App
