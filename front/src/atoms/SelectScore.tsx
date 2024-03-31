import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faHotdog,
  faStar,
  faMoneyBill1Wave,
  faChampagneGlasses,
} from '@fortawesome/free-solid-svg-icons';
import { ISelectScore } from '../types/components/SelectScore.types';

const ScoreStar = ({ score }: { score: number | undefined }) => {
  const scoreStar = Array.from({ length: 5 }, (v, i) => i < (score || 3));

  const handleClickStar = (input: number) => {
    if (!score) return;
    // atom으로 가져온 score를 setScore
    console.log(input);
  };

  return (
    <div>
      {scoreStar.map((element, idx) => (
        <FontAwesomeIcon
          icon={faStar}
          className={`${element ? 'text-error' : 'text-gray-dark'}`}
          onClick={() => handleClickStar(idx + 1)}
        />
      ))}
    </div>
  );
};

function SelectScore(props: ISelectScore) {
  return (
    <div className={`flex flex-col gap- ${props.customCSS}"`}>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faHotdog}
            className="text-secondary text-xl mr-2 w-5"
          />
          맛있어요!
        </span>
        <ScoreStar score={props.score?.[0]} />
      </div>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faMoneyBill1Wave}
            className="text-secondary text-xl mr-2 w-5"
          />
          가격이 괜찮아요!
        </span>
        <ScoreStar score={props.score?.[1]} />
      </div>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faChampagneGlasses}
            className="text-secondary text-xl mr-2 w-5"
          />
          분위기가 좋아요!
        </span>
        <ScoreStar score={props.score?.[2]} />
      </div>
    </div>
  );
}

export default SelectScore;
