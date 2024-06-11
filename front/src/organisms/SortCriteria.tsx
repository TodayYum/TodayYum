/**
 * SelectSort : 정렬 기준을
 * @returns
 */
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons';
// import SortBottomSheet from './SortBottomSheet';
// import CategoryBottomSheet from './CategoryBottomSheet';
import useSearchDataAtom from '../jotai/searchData';
import ToggleChip from '../atoms/ToggleChip';
import { CATEGORY_LIST, SORT_LIST } from '../constant/searchConstant';
import { ISortCriteria } from '../types/organisms/SortCriteria.type';

const [SELECT_NONE, SELECT_SORT, SELECT_CATEGORY] = [0, 1, 2];

function SortCriteria(props: ISortCriteria) {
  const [searchData] = useSearchDataAtom();
  const handleIcon = (type: number) => {
    if (props.isSelect === SELECT_NONE) {
      props.setIsSelect(type);
      return;
    }
    props.setIsSelect(SELECT_NONE);
  };
  return (
    <div className="mx-[30px] px-3 bg-white py-3 my-5 flex flex-col gap-2 h-[100%]">
      <div
        onClick={() => handleIcon(SELECT_SORT)}
        onKeyUp={() => {}}
        role="button"
        tabIndex={0}
      >
        {props.isSelect === SELECT_SORT ? (
          <FontAwesomeIcon icon={faChevronUp} className="mr-2" />
        ) : (
          <FontAwesomeIcon icon={faChevronDown} className="mr-2" />
        )}
        {SORT_LIST[searchData.sort]}
      </div>
      <div
        onClick={() => handleIcon(SELECT_CATEGORY)}
        onKeyUp={() => {}}
        role="button"
        tabIndex={0}
      >
        {props.isSelect === SELECT_CATEGORY ? (
          <FontAwesomeIcon
            icon={faChevronUp}
            onClick={() => props.setIsSelect(SELECT_NONE)}
            className="mr-2"
          />
        ) : (
          <FontAwesomeIcon
            icon={faChevronDown}
            onClick={() => props.setIsSelect(SELECT_CATEGORY)}
            className="mr-2"
          />
        )}
        카테고리
      </div>
      <div className="flex flex-wrap gap-2">
        {searchData.category.map(
          (category, idx) =>
            category && (
              <ToggleChip
                dataId={CATEGORY_LIST.kr[idx]}
                isClick={category}
                text={CATEGORY_LIST.kr[idx]}
                onClick={() => {}}
              />
            ),
        )}
      </div>
      {/* {props.isSelect === SELECT_CATEGORY && (
        <CategoryBottomSheet onClose={() => props.setIsSelect(SELECT_NONE)} />
      )}
      {props.isSelect === SELECT_SORT && (
        <SortBottomSheet onClose={() => props.setIsSelect(SELECT_NONE)} />
      )} */}
    </div>
  );
}

export default SortCriteria;
