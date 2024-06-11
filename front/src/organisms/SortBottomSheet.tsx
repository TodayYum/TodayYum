import { useEffect, useRef } from 'react';
import {
  ISelectBox,
  ISortBottomSheet,
} from '../types/organisms/SortBottomSheet.types';
import useSearchDataAtom from '../jotai/searchData';

const SelectBox = (props: ISelectBox) => {
  return (
    <div
      className="flex flex-col justify-center items-center mt-2"
      onClick={props.onClick}
      onKeyUp={() => {}}
      role="button"
      tabIndex={0}
    >
      <p className={`${props.isBold ? 'font-bold' : ''} text-xl mb-1`}>
        {props.text}
      </p>
      <hr className="w-1/3" />
    </div>
  );
};

function SortBottomSheet(props: ISortBottomSheet) {
  const [searchData, setSearchData] = useSearchDataAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const handleClickSelectBox = (idx: number) => {
    setSearchData({ sort: idx });
    props.onClose();
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
    <div className="bg-black/30 sm:w-[393px] w-screen h-[100%] absolute top-0 left-0">
      <div
        ref={modalRef}
        className="rounded-t-[40px] sm:w-[393px] bg-white pt-2 pb-5 fixed bottom-0 z-10 w-screen"
      >
        <p className="text-center text-xl font-bold text-gray-dark mb-4">
          정렬 기준 선택
        </p>
        {sortList.map((element, idx) => (
          <SelectBox
            text={element}
            id={idx}
            onClick={() => handleClickSelectBox(idx)}
            key={element}
            isBold={idx === searchData.sort}
          />
        ))}
      </div>
    </div>
  );
}

const sortList = ['최신순', '평점순', 'Yummy순'];
export default SortBottomSheet;
