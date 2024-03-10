/**
 * @param props : customClass - 외부에서 css 조절을 위한 class
 * SelectSearch : 메인 페이지에서 검색 화면으로 넘어가기 위한 단순한 모양을 담당하는 컴포넌트
 * @returns
 */

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';

function SelectSearch() {
  return (
    <Link
      className="flex bg-white rounded items-center mx-[30px] px-3 py-3 gap-5"
      to="/recent"
    >
      <p className="border-b-[1px] border-black w-full text-gray-dark pl-1 text-base">
        검색하기
      </p>
      <FontAwesomeIcon icon={faMagnifyingGlass} />
    </Link>
  );
}

export default SelectSearch;
