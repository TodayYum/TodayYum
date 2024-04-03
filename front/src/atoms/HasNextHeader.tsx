/**
 * @param: props
 */

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import { useNavigate, Link } from 'react-router-dom';
import { IHasNextHeader } from '../types/components/HasNextHeader.types';

function HasNextHeader(props: IHasNextHeader) {
  const navigate = useNavigate();
  return (
    <div className="fixed top-0 w-full py-[15px] flex bg-white">
      <FontAwesomeIcon
        icon={faAngleLeft}
        onClick={() => navigate(-1)}
        className="ml-[15px] text-xl mr-5 my-auto"
      />
      <div className="flex justify-between w-full pr-[15px]">
        <p className="font-bold text-xl">글 쓰기</p>
        {props.isActive ? (
          <Link
            to="/create-board"
            className="relative right-0 text-xl font-thin text-secondary font-ggTitle"
          >
            다음
          </Link>
        ) : (
          <p className="relative right-0 text-xl font-thin text-gray">다음</p>
        )}
      </div>
    </div>
  );
}

export default HasNextHeader;
