/**
 * @param props
 *
 * SupportText : 입력값 성공, 실패 유무에 따라 보조 텍스트를 보여주는 컴포넌트
 * InputTextBox : 이메일 등 텍스트 입력 받는 컴포넌트
 * customClass : 외부에서 조절하고 싶은 tailwindCSS 전달
 */
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { IInputText, ISupoortText } from '../types/components/InputText.types';

const SupportText = (props: ISupoortText) => {
  if (props.isSuccess === -1) {
    return <p className="mx-3 font-sm py-3 invisible">empty</p>;
  }
  if (props.isSuccess === 1) {
    return (
      <p className="text-left font-sm text-correct mx-2 py-3">{`${props.successText}`}</p>
    );
  }
  return (
    <p className="text-left font-sm text-error mx-2 py-3">{`${props.failText}`}</p>
  );
};

function InputText(props: IInputText) {
  const [isFocused, setIsFocused] = useState(false);

  const handleClickButton = () => {
    if (props.onClickUpload) {
      props.onClickUpload();
    }
  };
  return (
    <div className={`${props.customClass} w-full`}>
      <div
        className={`${isFocused ? 'border-black' : 'border-gray'} flex items-center px-3 rounded-small h-10 border-[1px] bg-white`}
      >
        <input
          type={props.type}
          placeholder={props.placeholder}
          className="w-full placeholder:font-ggTitle placeholder:text-sm disabled:bg-white font-sm rounded-small border-none focus:outline-none"
          onChange={e => props.setValue(e.target.value)}
          value={props.value}
          alt={`${props.type} 입력창`}
          disabled={props.disabled}
          onClick={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
        />
        {props.hasButton && (
          <FontAwesomeIcon icon={faArrowUp} onClick={handleClickButton} />
        )}
      </div>
      {props.hasSupport && (
        <SupportText
          isSuccess={props.isSuccess ?? -1}
          successText={props.successText ?? ''}
          failText={props.failText ?? ''}
        />
      )}
    </div>
  );
}

export default InputText;
