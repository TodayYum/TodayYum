/**
 * @param: props
 * IHeader - ateAt : 먹은 날짜
 */

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import { IHeader } from '../types/components/Header.types';

function Header(props: IHeader) {
  const navigate = useNavigate();
  return (
    <div className="fixed top-0 w-full py-[15px] flex">
      <FontAwesomeIcon
        icon={faAngleLeft}
        onClick={() => navigate(-1)}
        className="ml-[15px] text-xl mr-5 my-auto"
      />
      <div className="flex justify-between w-full pr-[15px]">
        <p className="font-bold text-xl">{props.title}</p>
        {props.ateAt && (
          <p className="relative right-0 text-xl font-thin">{props.ateAt}</p>
        )}
      </div>
    </div>
  );
}

export default Header;
