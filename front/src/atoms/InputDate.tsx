/**
 * InputDate : 기획 상 최근 날짜만 입력하게 되므로, 실젝 date 포맷을 선택하는게 아닌,
 * bottomSheet를 이용해 최근 일주일 중 날짜를 선택하도록 함. 본체는 text
 * @returns
 */

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
import { IInputDate } from '../types/components/InputDate.types';
import { DATE_LIST, TIME_LIST } from '../constant/createFilmConstant';

function InputDate(props: IInputDate) {
  return (
    <div className={`${props.customClass} w-full`}>
      <div
        className="border-gray flex items-center px-3 rounded-small h-10 border-[1px] bg-white"
        onClick={props.onClick}
        onKeyUp={() => {}}
        role="button"
        tabIndex={0}
      >
        <input
          type="text"
          placeholder={`${props.isTime ? '시간' : '날짜'}`}
          className="w-full placeholder:font-ggTitle placeholder:text-sm disabled:bg-white font-sm rounded-small border-none focus:outline-none"
          value={
            props.isTime ? TIME_LIST[props.value].kr : DATE_LIST[props.value]
          }
          alt={`${props.isTime ? '시간' : '날짜'} 입력창`}
        />
        {props.isOnSheet ? (
          <FontAwesomeIcon icon={faChevronUp} />
        ) : (
          <FontAwesomeIcon icon={faChevronDown} />
        )}
      </div>
    </div>
  );
}

export default InputDate;
