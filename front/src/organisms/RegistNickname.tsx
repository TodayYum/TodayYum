import { useQuery, useMutation } from '@tanstack/react-query';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { useSetNicknameAtom, useSignUpDataAtom } from '../jotai/signUpData';
import {
  fetchGetNicknameDuplicate,
  fetchPostSignUp,
} from '../services/userService';
import { ISignUpRequest } from '../types/services/userService';

/**
 * RegistNickname : 회원가입 절차 중, 닉네임 등록 및 최종 확인 버튼을 관리하는 organism
 * @returns
 */
function RegistNickname() {
  const [nickname, setNicknameState] = useState('');
  const setNickname = useSetNicknameAtom();
  const registData = useSignUpDataAtom();
  const navigate = useNavigate();

  const { data: checkNicknameResponse, isPending } = useQuery({
    queryKey: ['nicknameDuplicate', nickname],
    queryFn: () => fetchGetNicknameDuplicate(nickname),
    staleTime: 500000,
  });

  const { mutate: fetchSignUp } = useMutation({
    mutationFn: (signUpRequest: ISignUpRequest) =>
      fetchPostSignUp(signUpRequest),
    onSuccess: () => {
      navigate('/login');
    },
  });

  const handleOverlapCheck = () => {
    setNicknameState(registData.nickname);
  };

  const handleSubmitSignUp = () => {
    fetchSignUp({
      nickname: registData.nickname,
      email: registData.email,
      password: registData.password,
    });
  };

  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      <p className="base-bold ml-1">닉네임을 설정해주세요.</p>
      <div className="flex gap-4">
        <InputText
          hasSupport
          placeholder="닉네임"
          setValue={setNickname}
          type="text"
          failText="중복된 닉네임이 있습니다."
          isSuccess={isPending ? -1 : Number(checkNicknameResponse)}
          successText="사용가능한 닉네임입니다."
          value={registData.nickname}
          customClass="flex-[1_1_content]"
        />
        <RectangleButton
          onClick={handleOverlapCheck}
          text="중복검사"
          customClass="flex-[1_1_95px] h-[40px]"
        />
      </div>
      <RectangleButton
        onClick={handleSubmitSignUp}
        text="확인"
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

export default RegistNickname;
