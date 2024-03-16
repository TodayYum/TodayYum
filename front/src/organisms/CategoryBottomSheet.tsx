import { useEffect, useRef } from 'react';
import { ISortBottomSheet } from '../types/organisms/SortBottomSheet.types';
import useSearchDataAtom from '../jotai/searchData';
import ToggleChip from '../atoms/ToggleChip';
import { CATEGORY_LIST } from '../constant/searchConstant';
// const SelectBox = (props: ISelectBox) => {
//   return (
//     <div
//       className="flex flex-col justify-center items-center mt-2"
//       onClick={props.onClick}
//       onKeyUp={() => {}}
//       role="button"
//       tabIndex={0}
//     >
//       <p className={`${props.isBold ? 'font-bold' : ''} text-xl mb-1`}>
//         {props.text}
//       </p>
//       <hr className="w-1/3" />
//     </div>
//   );
// };

function CategoryBottomSheet(props: ISortBottomSheet) {
  const [searchData, setSearchData] = useSearchDataAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const handleClickChip = (idx: number) => {
    let convertedList = [...searchData.category];
    if (idx === 0) {
      convertedList = convertedList.map(() => false);
    } else {
      convertedList[0] = false;
    }
    convertedList[idx] = !convertedList[idx];
    setSearchData({ category: convertedList });
  };
  const handleModalClose = (e: MouseEvent) => {
    if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
      props.onClose();
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleModalClose);
    return () => {
      document.removeEventListener('mousedown', handleModalClose);
    };
  }, []);

  return (
    <div className="bg-black/30 w-screen h-[100%] absolute top-0 left-0">
      <div
        ref={modalRef}
        className="rounded-t-[40px] bg-white pt-2 pb-5 fixed bottom-0 z-10 w-screen"
      >
        <p className="text-center text-xl font-bold text-gray-dark mb-4">
          카테고리 선택
        </p>
        <div className="flex px-[30px] flex-wrap gap-4 ">
          {CATEGORY_LIST.map((element, idx) => (
            <ToggleChip
              dataId={`${idx}`}
              text={element}
              onClick={() => handleClickChip(idx)}
              isClick={searchData.category[idx]}
            />
          ))}
        </div>
      </div>
    </div>
  );
}

export default CategoryBottomSheet;
