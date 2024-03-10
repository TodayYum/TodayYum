import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faMagnifyingGlass,
  faAngleLeft,
} from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import useSearchDataAtom from '../jotai/searchData';

function SearchBar() {
  const [searchData, setSearchData] = useSearchDataAtom();
  const nagivate = useNavigate();

  return (
    <div className="flex bg-white items-center px-[15px] py-3 gap-5">
      <FontAwesomeIcon icon={faAngleLeft} onClick={() => nagivate(-1)} />
      <input
        type="text"
        placeholder="검색하기"
        value={searchData.keyword}
        onChange={e => setSearchData({ keyword: e.target.value })}
        className="focus:outline-none border-b-[1px] border-black w-full text-gray-dark pl-1 text-base"
      />
      <FontAwesomeIcon
        icon={faMagnifyingGlass}
        onClick={() => nagivate('/search-result')}
      />
    </div>
  );
}

export default SearchBar;
