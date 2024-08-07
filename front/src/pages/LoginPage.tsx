import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import InputPassword from '../atoms/InputPassword';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';

import { fetchPostSignin } from '../services/userService';
import { ISigninRequest } from '../types/services/userService';
import useSignInDataAtom from '../jotai/signInData';

function LoginPage() {
  const [id, setId] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [isFailed, setIsFailed] = useState<boolean>(false);
  const [, setSignInData] = useSignInDataAtom();
  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: (request: ISigninRequest) => fetchPostSignin(request),
    onSuccess: response => {
      setSignInData({
        memberId: response.memberId,
        nickname: response.nickname,
        token: response.token,
      });
      navigate('/');
    },
    onError: () => {
      setIsFailed(true);
    },
  });

  const SubmitLogin = () => {
    mutate({ email: id, password });
  };

  return (
    <div className="px-[45px] flex items-center flex-col h-screen justify-center bg-background min-w-[393px]">
      <img
        src="logo.png"
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
