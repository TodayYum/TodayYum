/**
 * @param
 * MainPage : 초기 페이지, 검색 버튼 눌렀을때 발생, 검색 컴포넌트 클릭 시 검색 관련 화면으로 연결
 * @returns
 */

import SelectSearch from '../atoms/SelectSearch';
import SortCriteria from '../organisms/SortCriteria';
import TodayYum from '../organisms/TodayYum';

function MainPage() {
  return (
    <div className="bg-background h-screen py-3">
      <SelectSearch />
      <TodayYum />
      <SortCriteria />
    </div>
  );
}

export default MainPage;
