import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faMagnifyingGlass,
  faCirclePlus,
  faUser,
  faBell,
} from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <div className="text-primary w-full text-[35px] flex justify-around fixed bottom-0 bg-white">
      <Link to="/">
        <FontAwesomeIcon icon={faMagnifyingGlass} />
      </Link>
      <Link to="/">
        <FontAwesomeIcon icon={faCirclePlus} />
      </Link>
      <Link to="/">
        <FontAwesomeIcon icon={faUser} />
      </Link>
      <Link to="/">
        <FontAwesomeIcon icon={faBell} />
      </Link>
    </div>
  );
}

export default Navbar;
