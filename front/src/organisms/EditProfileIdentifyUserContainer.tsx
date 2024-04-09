/**
 * EditProfileIdentifyUserContainer
 * 유저 정보 수정 중, 비밀번호 재설정을 클릭했을 경우 나타나는 영역
 * 재설정 이전 본인이 맞는지 한 번더 확인을 위해 비밀번호 입력하는 단계
 */

import { useState } from 'react';
import InputPassword from '../atoms/InputPassword';
import RectangleButton from '../atoms/RectangleButton';
import { IEditProfileIdentifyuserContainer } from '../types/organisms/EditProfileIdentifyUserContainer.types';

function EditProfileIdentifyUserContainer(
  props: IEditProfileIdentifyuserContainer,
) {
  const [password, setPassword] = useState('');
  return (
    <div>
      <p className="text-center font-bold text-2xl mb-6">본인 확인</p>
      <p className="base-bold mb-5 ml-1">비밀번호를 입력해주세요.</p>
      <InputPassword
        setValue={setPassword}
        value={password}
        customClass="mb-5"
      />
      <RectangleButton
        text="확인"
        onClick={props.goNextLevel}
        customClass="w-full mt-5"
      />
    </div>
  );
}

export default EditProfileIdentifyUserContainer;
