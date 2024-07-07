import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronDown,
  faChevronUp,
  faStar,
} from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
import useCreateFilmAtom from '../jotai/createFilm';
import ToggleChip from '../atoms/ToggleChip';
import { CATEGORY_LIST } from '../constant/searchConstant';
import CategoryBottomSheetForCreateFilm from './CategoryBottomSheetForCreateFilm';
import SelectScore from '../atoms/SelectScore';
import InputDate from '../atoms/InputDate';
import DateBottomSheet from './DateBottomSheet';
import MealTimeBottomSheet from './MealTimeBottomSheet';
// 글 수정에 대한 계획
// useLocation으로 수정인지 작성인지를 받고
// 수정이면 헤더 변경, useEffect로 createFilm을 API로 받아온 값으로 변경해주는 과정을 넣어주는 것

function CreateFilmFirstData() {
  const [isCategorySheet, setIsCategorySheet] = useState(false);
  const [isDateSheet, setIsDateSheet] = useState(false);
  const [isTimeSheet, setIsTimeSheet] = useState(false);
  const [createFilm, setCreateFilm] = useCreateFilmAtom();

  const handleScoreClick = (score: number, type: number) => {
    const prevScore = [...createFilm.score];
    prevScore[type] = score;
    setCreateFilm({ score: prevScore });
  };

  const handleDateClick = () => {
    setIsDateSheet(true);
  };

  const handleTimeClick = () => {
    setIsTimeSheet(true);
  };
  return (
    <div className="mx-[15px]">
      <p className="base-bold">먹은 날짜와 시간대를 알려주세요.</p>
      <div className="flex gap-4 mb-[30px]">
        <InputDate
          isOnSheet={isDateSheet}
          onClick={handleDateClick}
          value={createFilm.ateAt}
        />
        <InputDate
          isOnSheet={isTimeSheet}
          onClick={handleTimeClick}
          value={createFilm.mealTime}
          isTime
        />
      </div>
      <p className="base-bold">카테고리를 선택해주세요.</p>
      <div
        onClick={() => setIsCategorySheet(true)}
        onKeyUp={() => {}}
        role="button"
        tabIndex={0}
        className="flex justify-between h-[37px] items-center mb-[30px]"
      >
        <div>
          {/* 카테고리 */}
          {isCategorySheet ? (
            <FontAwesomeIcon icon={faChevronUp} className="mr-2" />
          ) : (
            <FontAwesomeIcon icon={faChevronDown} className="mr-2" />
          )}
          카테고리 선택
        </div>
        {createFilm.category > 0 && (
          <ToggleChip
            dataId={`${createFilm.category}`}
            text={CATEGORY_LIST.kr[createFilm.category]}
            isClick
            onClick={() => {}}
          />
        )}
      </div>
      {/* 평점 */}
      <p className="base-bold">평점</p>
      <SelectScore
        score={createFilm.score}
        setScore={handleScoreClick}
        customCSS="mt-[15px] mb-[30px]"
      />
      <div className="flex justify-between w-full items-end">
        <span className="base-bold mb-0">총점</span>
        <span className="font-bold text-xl">
          <FontAwesomeIcon
            icon={faStar}
            className="text-primary text-2xl mr-2"
          />
          {(
            Math.round(
              (10 * createFilm.score.reduce((prev, curr) => prev + curr)) / 3,
            ) / 10
          ).toFixed(1)}
        </span>
      </div>
      {/* 카테고리 bottomsheet */}
      {isCategorySheet && (
        <CategoryBottomSheetForCreateFilm
          onClose={() => setIsCategorySheet(false)}
        />
      )}
      {isDateSheet && (
        <DateBottomSheet
          selectedIdx={createFilm.date}
          onClose={() => setIsDateSheet(false)}
        />
      )}
      {isTimeSheet && (
        <MealTimeBottomSheet
          selectedIdx={createFilm.mealTime}
          onClose={() => setIsTimeSheet(false)}
        />
      )}
    </div>
  );
}

export default CreateFilmFirstData;
