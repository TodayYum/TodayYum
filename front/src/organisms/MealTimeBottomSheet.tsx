import { useEffect, useRef, useState } from 'react';
import useCreateFilmAtom from '../jotai/createFilm';
import { IDateBottomSheet } from '../types/organisms/DateBottomSheet.types';
import { TIME_LIST } from '../constant/createFilmConstant';

function MealTimeBottomSheet(props: IDateBottomSheet) {
  const [, setCreateFilm] = useCreateFilmAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const [selectedIdx, setSelectedIdx] = useState(props.selectedIdx);
  const handleSelectDate = (idx: number) => {
    setCreateFilm({ mealTime: idx });
    setSelectedIdx(idx);
    props.onClose();
  };

  const handleSheetClose = (e: MouseEvent) => {
    if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
      props.onClose();
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleSheetClose);
    return () => {
      document.removeEventListener('mousedown', handleSheetClose);
    };
  }, []);

  return (
    <div className="bg-black/30 sm:w-[393px] w-screen h-[100%] absolute top-0 left-0">
      <div
        ref={modalRef}
        className="rounded-t-[40px] sm:w-[393px] bg-white pt-2 pb-5 fixed bottom-0 z-10 w-screen"
      >
        <p className="text-center text-xl font-bold text-gray-dark mb-4">
          식사시간 선택
        </p>
        <div className="flex px-[30px] flex-wrap gap-4 justify-center">
          {TIME_LIST.map((element, idx) => (
            <div
              onClick={() => handleSelectDate(idx)}
              tabIndex={idx}
              role="button"
              onKeyUp={() => {}}
              className={`${selectedIdx === idx && 'font-bold'} w-1/2 border-b-[1px] border-gray text-center`}
              key={element.en}
            >
              <p className="text-xl">{element.kr}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default MealTimeBottomSheet;
