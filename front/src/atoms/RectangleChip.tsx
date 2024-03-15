import { IRectangleChip } from '../types/components/RectangleChip.types';

/**
 * RectangleChip : 오늘얌 팻말에 사용되는 직사각형 칩 (round 아주 약간 들어간 것)
 * @param props
 * @returns
 */

function RectangleChip(props: IRectangleChip) {
  return (
    <span className="bg-secondary-container my-auto rounded-small text-base py-[5px] px-2.5 border-secondary-container  border-[1.5px]">
      {props.text}
    </span>
  );
}

export default RectangleChip;
