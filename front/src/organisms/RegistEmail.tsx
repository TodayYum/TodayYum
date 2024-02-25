import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
// import { IRegistEmail } from '../types/organisms/RegistEmail.type';
import {
  useSetCodeAtom,
  useSetEmailAtom,
  usePlusSignUpLevelAtom,
  useRegistDataAtom,
} from '../jotai/signUpAtom';

function RegistEmail() {
  const registData = useRegistDataAtom();
  const setEmail = useSetEmailAtom();
  const setCode = useSetCodeAtom();
  const plusSignUpLevel = usePlusSignUpLevelAtom();

  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      <p className="font-bold text-base mb-[18px] ml-1"> 사용할 이메일</p>
      <InputText
        type="text"
        hasSupport={false}
        placeholder="이메일"
        setValue={setEmail}
        customClass="mb-10"
        value={registData.email}
      />
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
        onClick={plusSignUpLevel}
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

export default RegistEmail;
