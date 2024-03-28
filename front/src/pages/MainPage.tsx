/**
 * @param
 * MainPage : 초기 페이지, 검색 버튼 눌렀을때 발생, 검색 컴포넌트 클릭 시 검색 관련 화면으로 연결
 * @returns
 */

import SelectSearch from '../atoms/SelectSearch';
import PolaroidList from '../organisms/PolaroidList';
import SortCriteria from '../organisms/SortCriteria';
import TodayYum from '../organisms/TodayYum';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';

function MainPage() {
  return (
    <div className="bg-background h-screen py-3">
      <SelectSearch />
      <TodayYum />
      <SortCriteria />
      <PolaroidList polaroidList={DUMMY_POLARLIST} />
    </div>
  );
}

const DUMMY_POLARLIST: IPolaroidFilm[] = [
  {
    firstTag: '테스트테스트테스트테스트테스트테스트테스트테스트테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
];

export default MainPage;
