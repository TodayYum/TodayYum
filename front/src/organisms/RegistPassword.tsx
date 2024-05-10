/**
 * RegistPassword : 회원가입 과정 중, 비밀번호 및 닉네임 등록 관리 컴포넌트
 * @returns
 */

import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import InputPassword from '../atoms/InputPassword';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { isRightPassword } from '../util/passwordCheck';
import {
  useSetPasswordAtom,
  useSetConfirmPasswordAtom,
  usePlusSignUpLevelAtom,
  useSignUpDataAtom,
} from '../jotai/signUpData';
import { IRegistPassword } from '../types/organisms/RegistPassword.types';

function RegistPassword(props: IRegistPassword) {
  const registData = useSignUpDataAtom();
  const setPassword = useSetPasswordAtom();
  const setConfirmPassword = useSetConfirmPasswordAtom();
  const plusSignUpLevelAtom = usePlusSignUpLevelAtom();
  const navigate = useNavigate();
  const handleNextButton = () => {
    // 비밀번호 체크
    if (!isRightPassword(registData.password)) {
      Swal.fire({
        icon: 'warning',
        text: '비밀번호가 올바르지 않습니다.',
        width: '300px',
        confirmButtonText: '돌아가기',
        confirmButtonColor: '#787D85',
      });
      return;
    }
    // 비밀번호 일치성 체크
    if (
      handleSuccessOfConfirmPassword(
        registData.password,
        registData.confirmPassword,
      ) !== 1
    ) {
      Swal.fire({
        icon: 'warning',
        text: '입력한 비밀번호가 다릅니다!',
        width: '300px',
        confirmButtonText: '돌아가기',
        confirmButtonColor: '#787D85',
      });
      return;
    }
    if (props.isSignUp) {
      plusSignUpLevelAtom();
    } else {
      navigate('/login');
    }
  };
  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      {props.isSignUp && <p className="base-bold ml-1">이메일</p>}
      {props.isSignUp && (
        <InputText
          type="text"
          placeholder="아이디"
          value={registData.email}
          setValue={() => {}}
          hasSupport={false}
          disabled
          customClass="mb-5"
        />
      )}
      <p className="base-bold mb-5 ml-1">
        새로 설정할 비밀번호를 입력해주세요.
      </p>
      <InputPassword
        setValue={setPassword}
        value={registData.password}
        customClass="mb-5"
      />
      <p className="base-bold ml-1">비밀번호를 다시 입력해주세요.</p>
      <InputText
        type="password"
        hasSupport
        isSuccess={handleSuccessOfConfirmPassword(
          registData.password,
          registData.confirmPassword,
        )}
        successText="비밀번호가 일치합니다."
        failText="비밀번호가 일치하지 않습니다."
        placeholder="비밀번호 확인"
        setValue={setConfirmPassword}
        value={registData.confirmPassword}
      />
      <RectangleButton
        text="다음 단계로"
        onClick={handleNextButton}
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

const handleSuccessOfConfirmPassword = (origin: string, target: string) => {
  if (target.length === 0) {
    return -1;
  }
  if (origin === target) {
    return 1;
  }
  return 0;
};

export default RegistPassword;
