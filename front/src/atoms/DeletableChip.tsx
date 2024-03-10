import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { IDeletableChip } from '../types/components/DeletableChip.types';
/**
 *
 * @param props
 * onClick : 프론트에서 목록 제거 및 백으로 해당 검색어 제거 요청
 * @returns
 */

function DeletableChip(props: IDeletableChip) {
  const handleDelete = (event: React.MouseEvent) => {
    props.deleteSearchWord();
    event.stopPropagation();
  };
  return (
    <button
      type="button"
      className="bg-secondary-container my-auto flex items-center
      rounded-full text-base py-[5px] px-2.5 border-secondary-container  border-[1.5px]"
      onClick={props.onSelectClick}
    >
      <span className="text-base mr-2">{props.text}</span>
      <FontAwesomeIcon icon={faXmark} onClick={event => handleDelete(event)} />
    </button>
  );
}

export default DeletableChip;
