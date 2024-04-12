import { useEffect, useRef } from 'react';
import { ISortBottomSheet } from '../types/organisms/SortBottomSheet.types';
import useCreateFilmAtom from '../jotai/createFilm';
import ToggleChip from '../atoms/ToggleChip';
import { CATEGORY_LIST } from '../constant/searchConstant';

function CategoryBottomSheetForCreateFilm(props: ISortBottomSheet) {
  const [createFilm, setCreateFilm] = useCreateFilmAtom();
  const modalRef = useRef<HTMLDivElement>(null);
  const handleClickChip = (idx: number) => {
    if (createFilm.category === idx) {
      setCreateFilm({ category: 0 });
      return;
    }
    setCreateFilm({ category: idx });
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
          {CATEGORY_LIST.map(
            (element, idx) =>
              idx > 0 && (
                <ToggleChip
                  dataId={`${idx}`}
                  text={element}
                  onClick={() => handleClickChip(idx)}
                  isClick={createFilm.category === idx}
                />
              ),
          )}
        </div>
      </div>
    </div>
  );
}

export default CategoryBottomSheetForCreateFilm;
