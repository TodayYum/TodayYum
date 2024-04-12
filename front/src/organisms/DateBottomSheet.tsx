import { useEffect, useRef, useState } from 'react';
import useCreateFilmAtom from '../jotai/createFilm';
import { IDateBottomSheet } from '../types/organisms/DateBottomSheet.types';
import { DATE_LIST } from '../constant/createFilmConstant';

function DateBottomSheet(props: IDateBottomSheet) {
  const [, setCreateFilm] = useCreateFilmAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const [selectedIdx, setSelectedIdx] = useState(props.selectedIdx);
  console.log(selectedIdx, '뭐선택됨');

  const handleSelectDate = (idx: number) => {
    setCreateFilm({ date: idx });
    setSelectedIdx(idx);
    console.log(idx, '엥');
    props.onClose();
  };

  const handleSheetClose = (e: MouseEvent) => {
    if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
      props.onClose();
    }
  };

  const getDate = (idx: number) => {
    const currentDate = new Date();
    const date = new Date();
    date.setDate(currentDate.getDate() - idx);
    return date.toLocaleDateString().substring(5);
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleSheetClose);
    return () => {
      document.removeEventListener('mousedown', handleSheetClose);
    };
  }, []);

  return (
    <div className="bg-black/30 w-screen h-[100%] absolute top-0 left-0">
      <div
        ref={modalRef}
        className="rounded-t-[40px] bg-white pt-2 pb-5 fixed bottom-0 z-10 w-screen"
      >
        <p className="text-center text-xl font-bold text-gray-dark mb-8 mt-6">
          날짜 선택
        </p>
        <div className="flex px-[30px] gap-4 items-center flex-wrap justify-center">
          {DATE_LIST.map((element, idx) => (
            <div
              onClick={() => handleSelectDate(idx)}
              tabIndex={idx}
              role="button"
              onKeyUp={() => {}}
              className="text-xl w-[45%] font-ggTitle border-b-[1px] border-gray pl-3"
            >
              <span className={`${selectedIdx === idx && 'font-bold'} text-xl`}>
                {`${element} `}
              </span>
              <span className={`${selectedIdx === idx && 'font-bold'} text-lg`}>
                ({getDate(idx)})
              </span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default DateBottomSheet;
