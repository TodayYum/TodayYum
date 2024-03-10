/**
 * SearchPage : 검색 버튼 활성화시 나오는 페이지
 * 검색어 입력 전 : 최근 검색어 게시
 * 검색어 입력 및 엔터(돋보기 클릭) 후 : 3개 탭 및 결과 활성화
 * @returns
 */

import SearchBar from '../atoms/SearchBar';
import RecentSearch from '../organisms/RecentSearch';

function SearchPage() {
  return (
    <div>
      <SearchBar />
      <RecentSearch />
    </div>
  );
}

export default SearchPage;
