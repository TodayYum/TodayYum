import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faCircleCheck } from '@fortawesome/free-solid-svg-icons';
import {
  ICheckArea,
  IInputPassword,
} from '../types/components/InputPassword.types';
import {
  isLowerCase as isRightLowerCase,
  isRightLength as isRightLengthCase,
  hasNumbers as hasNumberscase,
} from '../util/passwordCheck';
/**
 * @param props
 * @returns
 */

const CheckArea = ({ isLowerCase, isRightLength, hasNumbers }: ICheckArea) => {
  const isCheckedIcon = (isChecked: boolean) => {
    return isChecked ? (
      <FontAwesomeIcon icon={faCircleCheck} />
    ) : (
      <FontAwesomeIcon icon={faCheck} />
    );
  };

  return (
    <div className="flex justify-center items-center gap-2 my-3">
      {isCheckedIcon(isRightLength)}
      <span className="mr-2">8글자 이상</span>
      {isCheckedIcon(hasNumbers)}
      <span className="mr-2">숫자</span>
      {isCheckedIcon(isLowerCase)}
      <span className="mr-2">영어 소문자</span>
    </div>
  );
};

function InputPassword(props: IInputPassword) {
  return (
    <div className={`${props.customClass} w-full`}>
      <input
        type="password"
        placeholder="비밀번호"
        className="w-full placeholder:font-ggTitle placeholder:text-sm  border-[1px] px-3 font-sm h-10 rounded-small border-gray focus:border-black focus:outline-none"
        onChange={e => props.setValue(e.target.value)}
        value={props.value}
        alt="비밀번호 입력창"
      />
      <CheckArea
        isLowerCase={isRightLowerCase(props.value)}
        isRightLength={isRightLengthCase(props.value)}
        hasNumbers={hasNumberscase(props.value)}
      />
    </div>
  );
}

export default InputPassword;
