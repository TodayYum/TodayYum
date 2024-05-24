import { useEffect, useRef, useState } from 'react';
import useCreateFilmAtom from '../jotai/createFilm';
import { IDateBottomSheet } from '../types/organisms/DateBottomSheet.types';
import { DATE_LIST } from '../constant/createFilmConstant';
import { getDate } from '../util/dateUtil';

function DateBottomSheet(props: IDateBottomSheet) {
  const [, setCreateFilm] = useCreateFilmAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const [selectedIdx, setSelectedIdx] = useState(props.selectedIdx);

  const handleSelectDate = (idx: number) => {
    setCreateFilm({ ateAt: idx });
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
                ({getDate(idx).substring(5)})
              </span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default DateBottomSheet;
