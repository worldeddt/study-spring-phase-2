

type Login = {
  username:string;
  password:string
}

const Login = () => {

  const LoginClick = () => {

  }
  return (
    <div>
      <input type="text"/>
      <button onClick={LoginClick}>카카오 로그인</button>
    </div>
  )
}

export default Login;