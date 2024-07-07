import { useRef, useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import Header from '../atoms/Header';
import SelectScore from '../atoms/SelectScore';
import { CATEGORY_LIST, CATEGORY_MAP } from '../constant/searchConstant';
import { ISOtoLocal } from '../util/dateUtil';
import { IBoardDetail } from '../types/jotai/updateFilm.types';
import { useUpdateFilmAtom, useUpdateFilmValueAtom } from '../jotai/updateFilm';
import InputTagChips from '../organisms/InputTagChips';
import { TIME_LIST, TIME_MAP } from '../constant/createFilmConstant';
import RectangleButton from '../atoms/RectangleButton';
import { fetchPatchFilm } from '../services/boardService';

function EditDetailPage() {
  const fixedContentsRef = useRef<HTMLDivElement>(null);
  const queryClient = useQueryClient();
  const boardDetail: IBoardDetail = useLocation().state.board;
  const [height, setHeight] = useState(490);
  const navigate = useNavigate();
  const { mutate } = useMutation({
    mutationFn: () => fetchPatchFilm(updateFilm),
    onSuccess: () => {
      queryClient.removeQueries({
        queryKey: ['boardDetail', String(boardDetail.id)],
        exact: true,
      });
      navigate(`/board/${boardDetail.id}`, { state: { isFromEdit: true } });
    },
  });
  const [updateFilm, setUpdateFilm] = [
    useUpdateFilmValueAtom(),
    useUpdateFilmAtom(),
  ];

  const handleSubmitUpdateFilm = () => {
    mutate();
  };

  const handleScoreClick = (score: number, type: number) => {
    let scoreSum =
      updateFilm.tasteScore + updateFilm.moodScore + updateFilm.priceScore;

    switch (type) {
      case 0:
        scoreSum += score - updateFilm.tasteScore;
        setUpdateFilm({
          tasteScore: score,
          totalScore: Math.round((10 * scoreSum) / 3) / 10,
        });
        break;

      case 1:
        scoreSum += score - updateFilm.priceScore;
        setUpdateFilm({
          priceScore: score,
          totalScore: Math.round((10 * scoreSum) / 3) / 10,
        });
        break;
      default:
        scoreSum += score - updateFilm.moodScore;
        setUpdateFilm({
          moodScore: score,
          totalScore: Math.round((10 * scoreSum) / 3) / 10,
        });
    }
  };

  const resizeObserver = new ResizeObserver(entries => {
    // 하나만 observe할 것이므로 forEach를 사용하지 않는다.
    if (entries[0].target === fixedContentsRef.current) {
      setHeight(entries[0].contentRect.height);
    }
  });

  useEffect(() => {
    if (fixedContentsRef.current) {
      resizeObserver.observe(fixedContentsRef.current);
    }

    return () => {
      resizeObserver.disconnect();
    };
  }, [fixedContentsRef.current]);

  const handleTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.target.value.length > 100) return;
    setUpdateFilm({ content: e.target.value });
  };
  return (
    <div>
      <Header
        title={CATEGORY_LIST.kr[CATEGORY_MAP[boardDetail.category]]}
        ateAt={`${ISOtoLocal(boardDetail.ateAt)} ${TIME_LIST[TIME_MAP[boardDetail.mealTime]].kr}`}
      />
      {/* 상단부 */}
      <div className="fixed overflow-hidden top-[58px]" ref={fixedContentsRef}>
        <DetailPageCarousel imgs={boardDetail.images} />
        {/* 프로필 */}
        <div className="bg-white flex justify-between p-[15px]">
          <div
            className="flex items-center gap-2"
            onClick={() =>
              navigate('/profile', {
                state: { memberId: boardDetail.memberId },
              })
            }
            role="button"
            onKeyUp={() => {}}
            tabIndex={0}
          >
            <img
              src={boardDetail.profile ?? '/default_profile.png'}
              alt="프로필사진"
              className="rounded-full h-7 w-7"
            />
            <span className="base-bold my-auto">{boardDetail.nickname}</span>
          </div>
        </div>
      </div>
      {/* 하단부 */}
      <div className="bg-white pt-[30px]" style={{ marginTop: height }}>
        <textarea
          className="focus:outline-secondary focus:outline-1 border-gray-dark border-[1px] rounded h-20 mx-[15px] mb-[30px] w-[calc(100%-30px)]"
          onChange={handleTextArea}
          value={updateFilm.content}
        />
        <InputTagChips />
        {/* <p className="text-base my-5">서울특별시 강남구 테헤란로 212</p> */}
        <div className="flex flex-row justify-between mt-[15px] px-[15px] pb-[30px]">
          <SelectScore
            score={[
              updateFilm.tasteScore,
              updateFilm.priceScore,
              updateFilm.moodScore,
            ]}
            setScore={handleScoreClick}
            customCSS="w-[280px]"
          />
          <span className="text-xl font-bold self-end">
            <FontAwesomeIcon
              icon={faStar}
              className="text-3xl text-error mr-1"
            />
            {updateFilm.totalScore}
          </span>
        </div>
      </div>
      <RectangleButton
        onClick={handleSubmitUpdateFilm}
        text="수정하기"
        customClass="w-[calc(100%-30px)] mt-[15px] mx-[15px]"
      />
      <div className="bg-black h-20 invisible ">asdf</div>
    </div>
  );
}

export default EditDetailPage;
