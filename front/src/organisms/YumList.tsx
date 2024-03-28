import { useRef, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose } from '@fortawesome/free-solid-svg-icons';
import { IYumList, IYumByCategory } from '../types/organisms/YumList.types';
import RectangleChip from '../atoms/RectangleChip';
import PolaroidFilm from './PolaroidFilm';

const YumByCategoy = (props: IYumByCategory) => {
  return (
    <div className="flex flex-col gap-4 justify-center w-1/2">
      <div className="flex justify-center">
        <RectangleChip text={props.title} />
      </div>
      <PolaroidFilm
        firstTag={props.board.firstTag}
        imgSrc={props.board.imgSrc}
        linkPage={props.board.linkPage}
        score={props.board.score}
        yummyCount={props.board.yummyCount}
        customCSS="mx-auto"
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
    <div className="bg-black/30 w-screen h-[135vh] absolute top-0 left-0">
      <div
        className="bg-white rounded mx-[30px] mt-[30px] px-2 py-[30px]"
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
          {DUMMY_TODAY_YUM_LIST.map(element => (
            <YumByCategoy title={element.title} board={element.board} />
          ))}
        </div>
      </div>
    </div>
  );
}
const DUMMY_TODAY_YUM_LIST = [
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
  {
    title: '한식',
    board: {
      firstTag: '테스트',
      imgSrc: '/logo.svg',
      score: 4.5,
      yummyCount: 30,
      linkPage: '/recent',
    },
  },
];

export default YumList;
