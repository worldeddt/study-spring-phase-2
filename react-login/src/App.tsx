import './App.css'
import {BrowserRouter as Router, useRoutes} from "react-router-dom";
import KakaoLoginButton from "./pages/KakaoLoginButton.tsx";
import KakaoAuthCallback from "./pages/KakaoAuthCallback.tsx";
import Home from "./pages/Home.tsx";
import {AuthProvider} from "./components/AuthProvider.tsx";
import {Main} from "./pages/Main.tsx";
// import {ThemeProvider} from "./components/ThemeProvider.tsx";
// import GrandChild from "./components/GrandChild.tsx";

const AppRoutes = () => {
  return useRoutes([
    { path: "/home", element: <Home/> },
    { path: "/", element: <KakaoLoginButton/> },
    { path: "/auth/kakao/callback", element: <KakaoAuthCallback/> },
    { path: "/main", element: <Main/> },
  ]);
};

function App() {

  // return (
  //   <ThemeProvider>
  //     <GrandChild/>
  //   </ThemeProvider>
  // );

  return (
    <AuthProvider>
      <Router>
        <AppRoutes/>
      </Router>
    </AuthProvider>
  )
}

export default App
