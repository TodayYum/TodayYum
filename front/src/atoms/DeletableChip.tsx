import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { IDeletableChip } from '../types/components/DeletableChip.types';
/**
 *
 * @param props
 * @returns
 */

function DeletableChip(props: IDeletableChip) {
  return (
    <button
      type="button"
      className="bg-secondary my-auto flex items-center
      rounded-full text-base py-[5px] px-2.5 border-secondary  border-[1.5px]"
    >
      <span className="text-base mr-2">{props.text}</span>
      <FontAwesomeIcon icon={faXmark} onClick={props.onClick} />
    </button>
  );
}

export default DeletableChip;
