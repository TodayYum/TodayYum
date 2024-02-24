import { IRectangleButton } from '../types/components/RectangleButton.types';

function RectangleButton(props: IRectangleButton) {
  return (
    <button
      type="button"
      onClick={props.onClick}
      className={`${props.isCancle ? 'bg-gray' : 'bg-primary'} rounded-[4px] w-full h-[45px]`}
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
