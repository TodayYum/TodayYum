import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import InputPassword from '../atoms/InputPassword';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { fetchLogin } from '../services/UserService';

const TEST_CONTROL_LOGIN = false;

function LoginPage() {
  const [id, setId] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [isFailed, setIsFailed] = useState<boolean>(false);
  const navigate = useNavigate();
  // const {data} = useQuery({
  //   queryKey: ['loginData'],
  //   queryFn: () =>
  // })

  // console.log(id, password);

  const SubmitLogin = () => {
    fetchLogin();
    if (TEST_CONTROL_LOGIN) {
      navigate('/');
    } else {
      setIsFailed(true);
    }
  };

  return (
    <div className="px-[45px] flex items-center flex-col h-screen justify-center bg-background">
      <img
        src="logo.svg"
        alt="logo img"
        className="w-[100px] h-[100px] mb-[90px]"
      />
      <InputText
        hasSupport={false}
        failText=""
        isSuccess={1}
        placeholder="아이디"
        successText=""
        type="text"
        setValue={setId}
        value={id}
        customClass="mb-[15px]"
      />
      <InputPassword
        setValue={setPassword}
        value={password}
        customClass="mb-2"
      />
      <p
        className={`${isFailed ? 'visible' : 'invisible'} text-error mb-6 text-sm`}
      >
        아이디 또는 비밀번호 정보가 잘못되었습니다.
      </p>
      <RectangleButton
        text="로그인"
        onClick={() => SubmitLogin()}
        customClass="w-full"
      />
      <div className="flex justify-center items-center my-[15px]">
        <Link to="/sign-up" className="mr-4 text-sm">
          회원가입
        </Link>{' '}
        |{' '}
        <Link to="/reset-password" className="ml-4 text-sm">
          비밀번호 찾기
        </Link>
      </div>
    </div>
  );
}

export default LoginPage;
