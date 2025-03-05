import {useTheme} from "./ThemeProvider.tsx";


const GrandChild = () => {
  const {theme, setTheme} = useTheme();

  const mode = (theme:string) => {

    setTheme(theme === "light" ? "dark" : "light")
    const body = document.getElementsByTagName("body")[0];

    if (theme === "light")
      body.setAttribute("style", "background-color: black");
    else
      body.setAttribute("style", "");
  }
  return (
    <div>
      <h1>현재 테마: {theme}</h1>
      <button onClick={() => {
        mode(theme);
      }}>
        테마 변경
      </button>
    </div>
  )
}


export default GrandChild;