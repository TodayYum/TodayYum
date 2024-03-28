/**
 * SearchPage : 검색 버튼 활성화시 나오는 페이지
 * 검색어 입력 전 : 최근 검색어 게시
 * 검색어 입력 및 엔터(돋보기 클릭) 후 : 3개 탭 및 결과 활성화
 * @returns
 */

import SearchTab from '../organisms/SearchTab';
import useSearchDataAtom from '../jotai/searchData';
import UserList from '../organisms/UserList';
import PolaroidList from '../organisms/PolaroidList';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';

// const TAB_TAG = 0;
// const TAB_REGION = 1;
const TAB_ACCOUNT = 2;

function SearchResultPage() {
  const [{ tab }] = useSearchDataAtom();
  return (
    <div>
      <SearchTab />
      {tab === TAB_ACCOUNT ? (
        <UserList />
      ) : (
        <PolaroidList polaroidList={DUMMY_POLARLIST} />
      )}
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

export default SearchResultPage;
