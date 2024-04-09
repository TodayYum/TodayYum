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

function Basebold({ text }: { text: string }) {
  return <p className="font-bold text-base mb-[18px] ml-1">{text}</p>;
}

function RegistEmail(props: IRegistEmail) {
  const registData = useSignUpDataAtom();
  const setEmail = useSetEmailAtom();
  const setCode = useSetCodeAtom();
  const plusSignUpLevel = usePlusSignUpLevelAtom();

  const handleNextButton = () => {
    if (registData.signUpLevel === 0) {
      // 여기에 Swal2로 이메일 확인하라는 문구 넣기
      plusSignUpLevel();
      return;
    }
    if (isCodeRight()) {
      plusSignUpLevel();
    }
  };
  return (
    <div className="h-screen flex flex-col justify-center px-[30px]">
      <Basebold text={props.isSignUp ? '사용할 이메일' : '작성한 이메일'} />

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
