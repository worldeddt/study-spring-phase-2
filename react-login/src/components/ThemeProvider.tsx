import {createContext, ReactNode, useContext, useState} from "react";


const ThemeContext = createContext<{
  theme: string;
  setTheme: (theme:string) => void
} | null >(null);

interface ThemeProviderProps {
  children: ReactNode;
}

export const ThemeProvider = ({ children } : ThemeProviderProps) => {

  const [theme, setTheme] = useState<string>("light");

  return (
    <ThemeContext.Provider value={{theme, setTheme}}>
      {children}
    </ThemeContext.Provider>
  )
}

export const useTheme = () => {
  const context = useContext(ThemeContext);

  if (!context) {
    throw new Error("useTheme must be used within a ThemeProvider");
  }

  return context;
};
