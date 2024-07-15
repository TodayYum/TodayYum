import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHotdog,
  faStar,
  faMoneyBill1Wave,
  faChampagneGlasses,
} from "@fortawesome/free-solid-svg-icons";
import {
  IScoreStar,
  ISelectScore,
} from "../types/components/SelectScore.types";

const ScoreStar = (props: IScoreStar) => {
  const scoreStar = Array.from({ length: 5 }, (_v, i) => i < props.score);

  const handleClickStar = (input: number) => {
    if (!props.setScore) return;
    if (props.score === input) {
      props.setScore(0, props.type);
    } else {
      props.setScore(input, props.type);
    }
  };

  return (
    <div>
      {scoreStar.map((element, idx) => (
        <FontAwesomeIcon
          icon={faStar}
          className={`${element ? "text-error" : "text-gray-dark"}`}
          onClick={() => handleClickStar(idx + 1)}
        />
      ))}
    </div>
  );
};

function SelectScore(props: ISelectScore) {
  return (
    <div className={`flex flex-col gap-2 ${props.customCSS}`}>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faHotdog}
            className="text-secondary text-xl mr-2 w-5"
          />
          맛있어요!
        </span>
        <ScoreStar score={props.score[0]} type={0} setScore={props.setScore} />
      </div>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faMoneyBill1Wave}
            className="text-secondary text-xl mr-2 w-5"
          />
          가격이 괜찮아요!
        </span>
        <ScoreStar score={props.score[1]} type={1} setScore={props.setScore} />
      </div>
      <div className="flex items-center justify-between">
        <span className="text-sm flex items-center">
          <FontAwesomeIcon
            icon={faChampagneGlasses}
            className="text-secondary text-xl mr-2 w-5"
          />
          분위기가 좋아요!
        </span>
        <ScoreStar score={props.score[2]} type={2} setScore={props.setScore} />
      </div>
    </div>
  );
}

export default SelectScore;
