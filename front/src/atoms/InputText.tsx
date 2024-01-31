import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faA, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { IInputText, ISupoortText } from '../types/components/InputText.types';
/**
 * @param props
 *
 * SupportText : 입력값 성공, 실패 유무에 따라 보조 텍스트를 보여주는 컴포넌트
 * InputTextBox : 이메일 등 텍스트 입력 받는 컴포넌트
 */

const SupportText = (props: ISupoortText) => {
  if (props.isSuccess === -1) {
    return <p />;
  }
  if (props.isSuccess === 1) {
    return (
      <p className="text-left font-sm text-correct mx-3 py-3">{`${props.successText}`}</p>
    );
  }
  return (
    <p className="text-left font-sm text-error py-3">{`${props.failText}`}</p>
  );
};

function InputText(props: IInputText) {
  const [isFocused, setIsFocused] = useState(false);
  return (
    <div className="px-normal">
      <div>
        <input
          type={props.type}
          placeholder={props.placeholder}
          className="w-full placeholder:font-ggTitle placeholder:text-sm border-[1px] px-3 font-sm h-10 rounded-small border-gray focus:border-black focus:outline-none"
          onChange={e => props.setValue(e.target.value)}
          value={props.value}
          alt={`${props.type} 입력창`}
          disabled={props.disabled}
        />
        {props.hasButton && props.onClickUpload && (
          <FontAwesomeIcon
            icon={faArrowUp}
            onClick={() => 
                props.onClickUpload();
              
            }
          />
        )}
      </div>
      <SupportText
        isSuccess={props.isSuccess}
        successText={props.successText}
        failText={props.failText}
      />
    </div>
  );
}

export default InputText;
