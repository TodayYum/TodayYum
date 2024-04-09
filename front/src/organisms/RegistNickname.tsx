import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { useSetNicknameAtom, useSignUpDataAtom } from '../jotai/signUpData';

function RegistNickname() {
  const setNickname = useSetNicknameAtom();
  const registData = useSignUpDataAtom();

  const handleOverlapCheck = () => {};

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
          isSuccess={isRightNickname(registData.nickname)}
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
        onClick={() => {}}
        text="확인"
        customClass="fixed bottom-20 w-[calc(100%-60px)]"
      />
    </div>
  );
}

const isRightNickname = (nickname: string) => {
  // nickname 기반으로 API 보내고 그 결과 받아오기
  if (nickname.length > 5) {
    return 1;
  }
  if (nickname.length > 3) {
    return 0;
  }
  return -1;
};

export default RegistNickname;
