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
import { isValidEmail } from '../util/emailCheck';
import {
  fetchCheckEmailDuplicate,
  fetchPostEmailCodeForSignup,
  fetchPostEmailCodeCheck,
  fetchPostEmailCodeForResetPassword,
} from '../services/userService';
import { IPostCodeRequest } from '../types/services/userService';
import { swalFail, swalSuccess } from '../constant/swalConstant';
import EmailCheck from '../constant/enums';
import setSwalText from '../util/swalUtil';

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
    mutationFn: (email: string) =>
      props.isSignUp
        ? fetchPostEmailCodeForSignup(email)
        : fetchPostEmailCodeForResetPassword(email),
    // onSuccess: () => {
    //   plusSignUpLevel();
    // },
    onError: () => {
      Swal.fire(setSwalText(swalFail, '인증 코드 생성에 실패했습니다.'));
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
    if (
      emailCheckResponse &&
      emailCheckResponse.result ===
        (props.isSignUp ? EmailCheck.VALID : EmailCheck.DUPLICATE)
    ) {
      makeEmailCode(emailQuery);
      plusSignUpLevel();
    }
  };

  // const handlePasswordResetNextButton = () => {
  //   makeEmailCode(emailQuery);
  // };

  const handleOverlapCheck = () => {
    if (!isValidEmail(registData.email)) return;
    setEmailQuery(registData.email);
  };

  useEffect(() => {
    console.log(emailCheckResponse);
    if (emailCheckResponse === undefined || emailCheckResponse.result === -1)
      return;
    let swalOptionObj;
    switch (emailCheckResponse.result) {
      case EmailCheck.VALID:
        swalOptionObj = props.isSignUp
          ? setSwalText(swalSuccess, '사용 가능한 이메일입니다.')
          : setSwalText(swalFail, '존재하지 않는 이메일입니다.');
        break;
      case EmailCheck.DUPLICATE:
        swalOptionObj = props.isSignUp
          ? setSwalText(swalFail, '이미 존재하는 이메일입니다.')
          : setSwalText(swalSuccess, '확인이 완료되었습니다.');
        break;
      case EmailCheck.INVALID:
        swalOptionObj = setSwalText(swalFail, '올바르지 않은 이메일입니다.');
        break;
      default:
        swalOptionObj = setSwalText(
          swalFail,
          '알 수 없는 오류가 발생했습니다.',
        );
    }
    Swal.fire(swalOptionObj);
  }, [emailCheckResponse]);

  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      <Basebold text={props.isSignUp ? '사용할 이메일' : '작성한 이메일'} />
      <div className="flex gap-4">
        <InputText
          type="email"
          hasSupport={false}
          placeholder="이메일"
          setValue={setEmail}
          customClass="mb-10 flex-[1_1_content]"
          value={registData.email}
          disabled={registData.signUpLevel > 0}
          isSuccess={0}
          successText=""
          failText=""
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
        // onClick={
        //   props.isSignUp ? handleNextButton : handlePasswordResetNextButton
        // }
        onClick={handleNextButton}
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

export default RegistEmail;
