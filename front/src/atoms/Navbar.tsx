import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faMagnifyingGlass,
  faCirclePlus,
  faUser,
  faBell,
} from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import useSignInDataAtom from '../jotai/signInData';

function Navbar() {
  const [signInData] = useSignInDataAtom();
  return (
    <div className="text-primary w-full text-[35px] flex justify-around fixed bottom-0 bg-white">
      <Link to="/">
        <FontAwesomeIcon icon={faMagnifyingGlass} />
      </Link>
      <Link to="/create-board/select-fictures">
        <FontAwesomeIcon icon={faCirclePlus} />
      </Link>
      <Link to="/profile" state={{ memberId: signInData.memberId }}>
        <FontAwesomeIcon icon={faUser} />
      </Link>
      <Link to="/">
        <FontAwesomeIcon icon={faBell} />
      </Link>
    </div>
  );
}

export default Navbar;
