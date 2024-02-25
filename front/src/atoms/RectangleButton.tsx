import { IRectangleButton } from '../types/components/RectangleButton.types';

function RectangleButton(props: IRectangleButton) {
  return (
    <button
      type="button"
      onClick={props.onClick}
      className={`${props.isCancle ? 'bg-gray' : 'bg-primary'} rounded-[4px] h-[45px] ${props.customClass}`}
    >
      <span
        className={`text-xl font-bold ${props.isCancle ? 'text-black' : 'text-white'}`}
      >
        {props.text}
      </span>
    </button>
  );
}

export default RectangleButton;
