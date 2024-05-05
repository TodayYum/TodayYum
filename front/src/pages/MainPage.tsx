/**
 * @param
 * MainPage : 초기 페이지, 검색 버튼 눌렀을때 발생, 검색 컴포넌트 클릭 시 검색 관련 화면으로 연결
 * @returns
 */
import { useState } from 'react';
import SelectSearch from '../atoms/SelectSearch';
import PolaroidList from '../organisms/PolaroidList';
import SortCriteria from '../organisms/SortCriteria';
import TodayYum from '../organisms/TodayYum';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import useIntersect from '../util/useIntersect';
// import { IPageInfo } from '../types/util/pageInfo';

function MainPage() {
  // const [pageInfo, setPageInfo] = useState<IPageInfo>({
  //   hasNext: true,
  //   pageNumber: 0,
  // });
  const test = async () => {
    const t = new Promise(resolve => setTimeout(resolve, 0));
    const t2 = await t;
    console.log(t2, '출력');
    return 4;
  };

  const [polaroidList, setPolaroidList] =
    useState<IPolaroidFilm[]>(DUMMY_POLARLIST);
  const [, setRef] = useIntersect(async (entry, observer) => {
    const newList = polaroidList.concat(
      JSON.parse(JSON.stringify(polaroidList)),
    );
    setPolaroidList(newList);
    /*
    // 불러올게 없을 때

    // 불러오는 함수 - 더미데이트
    const newList = polaroidList.concat(
      JSON.parse(JSON.stringify(polaroidList)),
    );
    setPolaroidList(newList);
    // 불러오는 함수 - API 연결
    */
    // 페이지 수 및 hasNext 조정
    observer.unobserve(entry.target);
    const length = await test();

    return length === 5;
  }, {});

  return (
    <div className="bg-background h-screen py-3">
      <SelectSearch />
      <TodayYum />
      <SortCriteria />
      <PolaroidList polaroidList={polaroidList} setRef={setRef} />
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
