import { useQuery } from '@tanstack/react-query';
import PolaroidFilm from './PolaroidFilm';
import YumList from './YumList';
import { ITodayYum } from '../types/organisms/TodayYum';
import { fetchGetTodayYummys } from '../services/boardService';
import { CATEGORY_LIST, CATEGORY_MAP } from '../constant/searchConstant';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';

function TodayYum(props: ITodayYum) {
  const { data: todayYum, isSuccess } = useQuery({
    queryKey: ['todayYum'],
    queryFn: () => fetchGetTodayYummys(true),
    staleTime: 500000,
  });
  const { data: todayYummys } = useQuery({
    queryKey: ['todayYummys'],
    queryFn: () => fetchGetTodayYummys(false),
    staleTime: 500000,
  });

  return (
    <div
      className="rounded bg-white mx-[30px] py-4 my-5"
      onClick={props.openModal}
      onKeyUp={() => {}}
      role="button"
      tabIndex={0}
    >
      <p className="base-bold text-center">오늘의 얌</p>
      <div className="flex justify-between ">
        {isSuccess && (
          <p className="shadow-md mx-auto bg-secondary-container base-bold my-auto rounded-small py-[5px] px-2.5 border-secondary-container leading-7  border-[1.5px] w-[40px]">
            {CATEGORY_LIST.kr[CATEGORY_MAP[todayYum.category]]}
          </p>
        )}
        {isSuccess && (
          <PolaroidFilm
            tag={todayYum.tag}
            thumbnail={todayYum.thumbnail}
            id={todayYum.id}
            totalScore={todayYum.totalScore}
            yummyCount={todayYum.yummyCount}
            category={todayYum.category}
            customCSS="pointer-events-none mr-4"
          />
        )}
      </div>
      {props.isShowModal && (
        <YumList
          onClose={props.closeModal}
          todayYummys={todayYummys as IPolaroidFilm[]}
        />
      )}
    </div>
  );
}

export default TodayYum;
