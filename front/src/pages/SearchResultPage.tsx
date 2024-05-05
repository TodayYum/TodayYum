/**
 * SearchPage : 검색 버튼 활성화시 나오는 페이지
 * 검색어 입력 전 : 최근 검색어 게시
 * 검색어 입력 및 엔터(돋보기 클릭) 후 : 3개 탭 및 결과 활성화
 * @returns
 */

import { useState } from 'react';
import SearchTab from '../organisms/SearchTab';
import useSearchDataAtom from '../jotai/searchData';
import UserList from '../organisms/UserList';
import PolaroidList from '../organisms/PolaroidList';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import useIntersect from '../util/useIntersect';
import { IUserThumbnail } from '../types/organisms/UserList';
// const TAB_TAG = 0;
// const TAB_REGION = 1;
const TAB_ACCOUNT = 2;

function SearchResultPage() {
  const [{ tab }] = useSearchDataAtom();
  const [polaroidList, setPolaroidList] =
    useState<IPolaroidFilm[]>(DUMMY_POLARLIST);
  const [userList, setUserList] = useState<IUserThumbnail[]>([]);
  const [, setRef] = useIntersect(async (entry, observer) => {
    const hasNext = true;
    // tab 결과에 따라 계정이면 계정 관련 API, 그 외의 경우엔 폴라로이드 API를 요청한다.
    if (tab === TAB_ACCOUNT) {
      const newList = userList.concat(JSON.parse(JSON.stringify(userList)));
      setUserList(newList);
    } else {
      const newList = polaroidList.concat(
        JSON.parse(JSON.stringify(polaroidList)),
      );
      setPolaroidList(newList);
    }
    // API 결과에 따라 hasNext 값을 할당하여 리턴한다.
    observer.unobserve(entry.target);
    return hasNext;
  }, {});
  return (
    <div>
      <SearchTab />
      {tab === TAB_ACCOUNT ? (
        <UserList userList={userList} setRef={setRef} />
      ) : (
        <PolaroidList polaroidList={polaroidList} setRef={setRef} />
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
