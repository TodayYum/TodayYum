import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faCircleCheck } from '@fortawesome/free-solid-svg-icons';
import {
  ICheckArea,
  IInputPassword,
} from '../types/components/InputPassword.types';

/**
 * @param props
 * @returns
 */

const CheckArea = ({
  isUpperCase,
  isLowerCase,
  isSpecialCharacter,
}: ICheckArea) => {
  const isCheckedIcon = (isChecked: boolean) => {
    return isChecked ? (
      <FontAwesomeIcon icon={faCircleCheck} />
    ) : (
      <FontAwesomeIcon icon={faCheck} />
    );
  };

  return (
    <div className="flex justify-center items-center gap-2 my-3">
      {/* <div> */}
      {isCheckedIcon(isSpecialCharacter)}
      <span className="mr-2">특수문자</span>
      {/* </div>
      <div> */}
      {isCheckedIcon(isUpperCase)}
      <span className="mr-2">영어 대문자</span>
      {/* </div>
      <div> */}
      {isCheckedIcon(isLowerCase)}
      <span className="mr-2">영어 소문자</span>
      {/* </div> */}
    </div>
  );
};

function InputPassword(props: IInputPassword) {
  return (
    <div className="px-normal">
      <input
        type="password"
        placeholder="비밀번호"
        className="w-full placeholder:font-ggTitle placeholder:text-sm  border-[1px] px-3 font-sm h-10 rounded-small border-gray focus:border-black focus:outline-none"
        onChange={e => props.setValue(e.target.value)}
        value={props.value}
        alt="비밀번호 입력창"
      />
      <CheckArea
        isLowerCase={isLowerCase(props.value)}
        isSpecialCharacter={isSpecialCharacter(props.value)}
        isUpperCase={isUpperCase(props.value)}
      />
    </div>
  );
}

const isUpperCase = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/
  const upperRegex = /^(?=.*[A-Z]).{1,}$/;

  return upperRegex.test(password);
};

const isSpecialCharacter = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/

  const speciatlChacracterRegex = /^(?=.*[!@#$%^&*?_]).{1,}$/;
  return speciatlChacracterRegex.test(password);
};

const isLowerCase = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/

  const lowerRegex = /^(?=.*[a-z]).{1,}$/;
  return lowerRegex.test(password);
};

export default InputPassword;
