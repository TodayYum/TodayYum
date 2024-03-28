import { useState } from 'react';
import PolaroidFilm from './PolaroidFilm';
import YumList from './YumList';

function TodayYum() {
  const todayYumData = DUMMY_POLAROID;
  const [isShowModal, setIsShowModal] = useState(false);

  const openModal = () => {
    setIsShowModal(true);
  };
  return (
    <div
      className="rounded bg-white mx-[30px] py-4 my-5"
      onClick={openModal}
      onKeyUp={() => {}}
      role="button"
      tabIndex={0}
    >
      <p className="base-bold text-center">오늘의 얌</p>
      <div className="flex justify-between ">
        <p className="shadow-md mx-auto bg-secondary-container base-bold my-auto rounded-small py-[5px] px-2.5 border-secondary-container leading-7  border-[1.5px] w-[40px]">
          한식
        </p>
        <PolaroidFilm
          firstTag={todayYumData.firstTag}
          imgSrc={todayYumData.imgSrc}
          linkPage={todayYumData.linkPage}
          score={todayYumData.score}
          yummyCount={todayYumData.yummyCount}
          customCSS="pointer-events-none mr-4"
        />
      </div>
      {isShowModal && (
        <YumList
          onClose={() => {
            setIsShowModal(false);
          }}
        />
      )}
    </div>
  );
}

const DUMMY_POLAROID = {
  firstTag: '테스트',
  imgSrc: '/logo.svg',
  score: 4.5,
  yummyCount: 30,
  linkPage: '/recent',
};

export default TodayYum;
