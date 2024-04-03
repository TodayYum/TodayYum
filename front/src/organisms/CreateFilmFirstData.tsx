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

function CreateFilmFirstData() {
  const [isOnBottomSheet, setIsOnBottomSheet] = useState(false);
  const [createFilm, setCreateFilm] = useCreateFilmAtom();

  const handleScoreClick = (score: number, type: number) => {
    const prevScore = [...createFilm.score];
    prevScore[type] = score;
    setCreateFilm({ score: prevScore });
  };

  return (
    <div>
      <p className="base-bold mx-[15px]">카테고리를 선택해주세요.</p>
      <div
        onClick={() => setIsOnBottomSheet(true)}
        onKeyUp={() => {}}
        role="button"
        tabIndex={0}
        className="mx-[15px]"
      >
        {/* 카테고리 */}
        {isOnBottomSheet ? (
          <FontAwesomeIcon icon={faChevronUp} className="mr-2" />
        ) : (
          <FontAwesomeIcon icon={faChevronDown} className="mr-2" />
        )}
        카테고리 선택
      </div>
      <div className="flex flex-wrap gap-2 m-[15px] mb-[30px]">
        {createFilm.category.map(
          (element, idx) =>
            element && (
              <ToggleChip
                dataId={`${idx}`}
                text={CATEGORY_LIST[idx]}
                isClick
                onClick={() => {}}
              />
            ),
        )}
      </div>
      {/* 평점 */}
      <p className="base-bold mx-[15px]">평점</p>
      <SelectScore
        score={createFilm.score}
        setScore={handleScoreClick}
        customCSS="mx-[15px] mb-[30px]"
      />
      <div className="flex justify-between w-[calc(100%-30px)] items-end mx-[15px]">
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
      {isOnBottomSheet && (
        <CategoryBottomSheetForCreateFilm
          onClose={() => setIsOnBottomSheet(false)}
        />
      )}
    </div>
  );
}

export default CreateFilmFirstData;
