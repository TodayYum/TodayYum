/**
 * ResetPassword : 비밀번호 재설정 컴포넌트
 * @returns
 */

import Swal from 'sweetalert2';
import { useState } from 'react';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { isRightPassword } from '../util/passwordCheck';
import InputPassword from '../atoms/InputPassword';
import { IResetPassword } from '../types/organisms/ResetPassword.types';

function ResetPassword(props: IResetPassword) {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  // const setNickname = useSetNicknameAtom();

  const handleNextButton = () => {
    // 닉네임 체크?
    // 비밀번호 체크
    if (!isRightPassword(password)) {
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
    if (handleSuccessOfConfirmPassword(password, confirmPassword) !== 1) {
      Swal.fire({
        icon: 'warning',
        text: '입력한 비밀번호가 다릅니다!',
        width: '300px',
        confirmButtonText: '돌아가기',
        confirmButtonColor: '#787D85',
      });
      return;
    }
    // 다음 단께로 넘어가는 작업
    props.setModalLevel(0);
  };

  return (
    <div className="flex flex-col justify-center my-5">
      <p className="text-center font-bold text-2xl mb-6">비밀번호 재설정</p>
      <p className="base-bold mb-5 ml-1">
        새로 설정할 비밀번호를 입력해주세요.
      </p>
      <InputPassword
        setValue={setPassword}
        value={password}
        customClass="mb-5"
      />
      <p className="base-bold ml-1">비밀번호를 다시 입력해주세요.</p>
      <InputText
        type="password"
        hasSupport
        isSuccess={handleSuccessOfConfirmPassword(password, confirmPassword)}
        successText="비밀번호가 일치합니다."
        failText="비밀번호가 일치하지 않습니다."
        placeholder="비밀번호 확인"
        setValue={setConfirmPassword}
        value={confirmPassword}
      />
      <RectangleButton
        text="재설정하기"
        onClick={handleNextButton}
        customClass="w-full mt-5"
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

export default ResetPassword;
