import Swal from 'sweetalert2';
import { useEffect, useRef } from 'react';
import { useQuery } from '@tanstack/react-query';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
// import { IRegistEmail } from '../types/organisms/RegistEmail.type';
import {
  useSetCodeAtom,
  useSetEmailAtom,
  usePlusSignUpLevelAtom,
  useSignUpDataAtom,
} from '../jotai/signUpData';
import { IRegistEmail } from '../types/organisms/RegistEmail.types';
import { isValidEmailByNumber } from '../util/emailCheck';
import { fetchCheckEmailDuplicate } from '../services/UserService';

function Basebold({ text }: { text: string }) {
  return <p className="font-bold text-base mb-[18px] ml-1">{text}</p>;
}

function RegistEmail(props: IRegistEmail) {
  const registData = useSignUpDataAtom();
  const setEmail = useSetEmailAtom();
  const setCode = useSetCodeAtom();
  const plusSignUpLevel = usePlusSignUpLevelAtom();
  const isFineEmail = useRef(false);
  // const [isTest, setIsTest] = useState(false);
  const { data: isFineEmailQ, refetch } = useQuery({
    queryKey: ['checkEmailDuplicate', registData.email],
    queryFn: () => fetchCheckEmailDuplicate(registData.email),
    enabled: false,
    staleTime: 0,
  });
  const handleNextButton = () => {
    // 이메일 중복 확인 상태에서 다음으로
    if (registData.signUpLevel === 0 && isFineEmail.current) {
      plusSignUpLevel();
      return;
    }
    // 인증코드 정상일 경우 다음으로
    if (registData.signUpLevel > 0 && isCodeRight()) {
      plusSignUpLevel();
    }
  };
  // 버튼 누를때만 refetch
  // 버튼 누를때만 그 결과를 판단한다
  // useEffect를 쓰는 순간 변화 감지이기 때문에 쓰기 곤란하다
  //
  const handleOverlapCheck = () => {
    // setIsTest(true);
    // 이메일 중복 체크 API 요청
    refetch();
    // const test = true;
    // if (isFineEmailQ) {
    //   // API 성공
    //   Swal.fire({
    //     icon: 'success',
    //     text: '사용가능한 이메일입니다.',
    //     width: '300px',
    //     confirmButtonText: '돌아가기',
    //     confirmButtonColor: '#787D85',
    //   });
    //   isFineEmail.current = true;
    // } else {
    //   // API 실패
    //   Swal.fire({
    //     icon: 'warning',
    //     text: '이미 사용중인 이메일입니다.',
    //     width: '300px',
    //     confirmButtonText: '돌아가기',
    //     confirmButtonColor: '#787D85',
    //   });
    //   isFineEmail.current = false;
    // }
  };

  useEffect(() => {
    console.log(isFineEmailQ);
    if (isFineEmailQ === undefined) return;
    if (isFineEmailQ) {
      // API 성공
      Swal.fire({
        icon: 'success',
        text: '사용가능한 이메일입니다.',
        width: '300px',
        confirmButtonText: '돌아가기',
        confirmButtonColor: '#787D85',
      });
      isFineEmail.current = true;
    } else {
      // API 실패
      Swal.fire({
        icon: 'warning',
        text: '이미 사용중인 이메일입니다.',
        width: '300px',
        confirmButtonText: '돌아가기',
        confirmButtonColor: '#787D85',
      });
      isFineEmail.current = false;
    }
  }, [isFineEmailQ]);

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
          isSuccess={isValidEmailByNumber(registData.email)}
          successText={
            registData.signUpLevel === 0 ? '올바른 형식의 이메일입니다.' : ''
          }
          failText="이메일 형식이 올바르지 않습니다."
        />
        <RectangleButton
          onClick={handleOverlapCheck}
          text="중복검사"
          customClass="flex-[1_1_95px] h-[40px]"
        />
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
        onClick={handleNextButton}
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

const isCodeRight = () => {
  return true;
};

export default RegistEmail;
