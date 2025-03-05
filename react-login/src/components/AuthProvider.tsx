import {createContext, ReactNode, useContext, useEffect, useRef, useState} from "react";
import axios from "axios";

const AuthContext = createContext <
  {
    isAuthenticated: boolean;
    setIsAuthenticated: (isAuthenticated: boolean) => void
  } |
  false > (false);

type authProps = {
  children: ReactNode
}

export const AuthProvider = ({children} : authProps) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const isRoaded = useRef<boolean>(false);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get("http://localhost:8081/api/auth/check", { withCredentials: true });
        setIsAuthenticated(response.data);
      } catch (error) {
        console.error(error);
        setIsAuthenticated(false);
      }
    }

    if (!isRoaded.current) checkAuth();

    isRoaded.current = true;
  }, []);

  return (
    <AuthContext.Provider value={{isAuthenticated, setIsAuthenticated}}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => {
  const authContext = useContext(AuthContext);

  if (!authContext) {
    throw new Error("사용자 권한이 없습니다.");
  }

  return authContext;
}