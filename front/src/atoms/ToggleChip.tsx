/**
 * @param props
 * ToggleChip
 * 클릭에 따라 토글 기능이 있는 칩 구현
 */

import { IToggleChip } from '../types/components/ToggleChip.types';

function ToggleChip(props: IToggleChip) {
  return (
    <button
      type="button"
      onClick={props.onClick}
      className={`${
        props.isClick ? 'bg-secondary' : 'bg-white'
      } rounded-full text-base py-[5px] px-2.5 border-secondary  border-[1.5px]`}
    >
      {props.text}
    </button>
  );
}

export default ToggleChip;
