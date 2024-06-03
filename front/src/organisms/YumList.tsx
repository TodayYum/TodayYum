import { useRef, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose } from '@fortawesome/free-solid-svg-icons';
import { IYumList, IYumByCategory } from '../types/organisms/YumList.types';
import RectangleChip from '../atoms/RectangleChip';
import PolaroidFilm from './PolaroidFilm';
import { CATEGORY_LIST, CATEGORY_MAP } from '../constant/searchConstant';

const YumByCategoy = (props: IYumByCategory) => {
  return (
    <div className="flex flex-col gap-4 justify-center w-1/2">
      <div className="flex justify-center">
        <RectangleChip
          text={CATEGORY_LIST.kr[CATEGORY_MAP[props.board.category]]}
        />
      </div>
      <PolaroidFilm
        tag={props.board.tag}
        thumbnail={props.board.thumbnail}
        id={props.board.id}
        totalScore={props.board.totalScore}
        yummyCount={props.board.yummyCount}
        customCSS="mx-auto"
        category={props.board.category}
      />
    </div>
  );
};

function YumList(props: IYumList) {
  const modalRef = useRef<HTMLDivElement>(null);
  const handleModelClose = (e: MouseEvent) => {
    if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
      props.onClose();
    }
  };

  const handleClose = (e: React.MouseEvent) => {
    e.stopPropagation();
    props.onClose();
  };
  useEffect(() => {
    document.addEventListener('mousedown', handleModelClose);
    return () => {
      document.removeEventListener('mousedown', handleModelClose);
    };
  }, []);

  return (
    <div className="bg-black/30 sm:w-[393px] w-screen h-screen absolute top-0 left-0">
      <div
        className="bg-white rounded mx-[30px] mt-[30px] px-2 py-[30px] h-[85%] overflow-auto"
        ref={modalRef}
      >
        <div className="relative">
          <p className="font-bold text-xl text-center mb-[30px]">오늘의 얌</p>
          <FontAwesomeIcon
            icon={faClose}
            className="absolute top-0 right-4 text-2xl text-gray-dark"
            onClick={handleClose}
          />
        </div>
        <div className="flex-wrap flex gap-y-8">
          {props.todayYummys.map(element => (
            <YumByCategoy board={element} key={element.id} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default YumList;
