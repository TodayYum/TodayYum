import Swal from 'sweetalert2';
import { useEffect, useState } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import {
  useSetCodeAtom,
  useSetEmailAtom,
  usePlusSignUpLevelAtom,
  useSignUpDataAtom,
} from '../jotai/signUpData';
import { IRegistEmail } from '../types/organisms/RegistEmail.types';
import { isValidEmailByNumber, isValidEmail } from '../util/emailCheck';
import {
  fetchCheckEmailDuplicate,
  fetchPostEmailCodeForSignup,
  fetchPostEmailCodeCheck,
} from '../services/userService';
import { IPostCodeRequest } from '../types/services/userService';
import { swalFail, swalSuccess } from '../constant/swalConstant';

function Basebold({ text }: { text: string }) {
  return <p className="font-bold text-base mb-[18px] ml-1">{text}</p>;
}

/**
 * RegistEmail : 회원가입 절차 중, 이메일 및 인증코드 입력을 담당하는 organism
 * @param props IRegistEmail
 * @returns
 */
function RegistEmail(props: IRegistEmail) {
  const registData = useSignUpDataAtom();
  const setEmail = useSetEmailAtom();
  const setCode = useSetCodeAtom();
  const plusSignUpLevel = usePlusSignUpLevelAtom();
  const [emailQuery, setEmailQuery] = useState('');
  const { data: emailCheckResponse } = useQuery({
    queryKey: ['checkEmailDuplicate', emailQuery],
    queryFn: () => fetchCheckEmailDuplicate(emailQuery),
    staleTime: 500000,
  });
  const { mutate: makeEmailCode } = useMutation({
    mutationFn: (email: string) => fetchPostEmailCodeForSignup(email),
    onSuccess: () => {
      plusSignUpLevel();
    },
    onError: err => {
      console.log('ㅇ이잉', err);
    },
  });
  const { mutate: validateEmailCode } = useMutation({
    mutationFn: (request: IPostCodeRequest) => fetchPostEmailCodeCheck(request),
    onSuccess: () => {
      plusSignUpLevel();
    },
    onError: () => {
      swalFail.text = '인증코드가 일치하지 않습니다.';
      Swal.fire(swalFail);
    },
  });

  const handleNextButton = () => {
    if (registData.email.length === 0) return;

    // 중복 체크 및 인증 코드 생성 후, 인증코드 확인
    if (registData.signUpLevel === 1) {
      validateEmailCode({ code: registData.code, email: registData.email });
      return;
    }

    // 이메일 중복 체크 성공한 상태에서 nextButton 눌렀을 경우 code 생성
    if (emailCheckResponse && emailCheckResponse.result) {
      makeEmailCode(emailQuery);
    }
  };

  const handlePasswordResetNextButton = () => {
    makeEmailCode(emailQuery);
  };

  const handleOverlapCheck = () => {
    if (!isValidEmail(registData.email)) return;
    setEmailQuery(registData.email);
  };

  useEffect(() => {
    if (emailCheckResponse === undefined || emailCheckResponse.result === -1)
      return;
    if (emailCheckResponse.result) {
      // API 성공
      swalSuccess.text = '사용 가능한 이메일입니다.';
      Swal.fire(swalSuccess);
    } else {
      // API 실패
      swalFail.text = '사용 불가능한 이메일입니다.';
      Swal.fire(swalFail);
    }
  }, [emailCheckResponse]);

  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      <Basebold text={props.isSignUp ? '사용할 이메일' : '작성한 이메일'} />
      <div className="flex gap-4">
        <InputText
          type="email"
          hasSupport
          placeholder="이메일"
          setValue={setEmail}
          customClass="mb-10 flex-[1_1_content]"
          value={registData.email}
          disabled={registData.signUpLevel > 0}
          isSuccess={
            emailCheckResponse?.result === false
              ? 0
              : isValidEmailByNumber(registData.email)
          }
          successText={
            registData.signUpLevel === 0 ? '올바른 형식의 이메일입니다.' : ''
          }
          failText="사용중이거나 잘못된 입력값입니다."
        />
        {registData.signUpLevel === 0 && (
          <RectangleButton
            onClick={handleOverlapCheck}
            text={props.isSignUp ? '중복검사' : '이메일 확인'}
            customClass="flex-[1_1_95px] h-[40px]"
          />
        )}
      </div>
      {registData.signUpLevel > 0 && (
        <p className="font-bold text-base mb-[18px] ml-1"> 인증코드 입력</p>
      )}
      {registData.signUpLevel > 0 && (
        <InputText
          type="text"
          hasSupport={false}
          placeholder="인증코드"
          setValue={setCode}
          value={registData.code}
        />
      )}

      <RectangleButton
        text="다음 단계로"
        onClick={
          props.isSignUp ? handleNextButton : handlePasswordResetNextButton
        }
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

export default RegistEmail;
