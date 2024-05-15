import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faStar } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';

function PolaroidFilm(props: IPolaroidFilm) {
  return (
    <Link
      to={`/board/${props.id}`}
      className={`${props.customCSS} bg-white rounded shadow-md min-w-[133px] max-w-[158px] w-[50%] min-h-[193px] max-h-[229px] h-[70vw] p-[10px] flex flex-col
    justify-between `}
    >
      <img src={props.thumbnail} alt="사진" className="w-full bg-blue" />
      <div className="flex justify-between">
        <p className="line-clamp-2 w-2/3">#{props.tag}</p>
        <div className="text-[10px]">
          <div>
            <FontAwesomeIcon icon={faStar} className="text-primary-container" />
            <span className="text-right"> {props.totalScore}</span>
          </div>
          <div>
            <FontAwesomeIcon
              icon={faHeart}
              className="text-primary-container"
            />
            <span className="text-right"> {props.yummyCount}</span>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default PolaroidFilm;
