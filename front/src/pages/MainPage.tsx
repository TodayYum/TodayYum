/**
 * MainPage : 초기 페이지, 검색 버튼 눌렀을때 발생, 검색 컴포넌트 클릭 시 검색 관련 화면으로 연결
 * @param
 * @returns
 */
import { useMemo, useState } from 'react';
import SelectSearch from '../atoms/SelectSearch';
import PolaroidList from '../organisms/PolaroidList';
import SortCriteria from '../organisms/SortCriteria';
import TodayYum from '../organisms/TodayYum';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import { fetchGetBoardList } from '../services/boardService';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';
import { IPageableResponse } from '../types/services/boardService';
import useSearchDataAtom from '../jotai/searchData';
import { SORT_LIST_EN } from '../constant/searchConstant';
// import { fetchPostRefreshToken } from '../services/userService';

const getCategories = (input: boolean[]) => {
  const categories: number[] = [];
  input.forEach((element, idx) => {
    if (element) {
      categories.push(idx);
    }
  });
  if (categories.length === 0) {
    categories.push(0);
  }
  return categories;
};

function MainPage() {
  const [isShowModal, setIsShowModal] = useState(false);
  const [searchData] = useSearchDataAtom();
  const [, setRef, data] = useInfiniteQueryProduct(
    {
      constant: 'boardList',
      variables: getCategories(searchData.category),
      stringVariables: [SORT_LIST_EN[searchData.sort]],
    },
    () => fetchGetBoardList,
    {},
  );

  if (!localStorage.getItem('recentSearch')) {
    const emptyList: string[] = [];
    localStorage.setItem('recentSearch', JSON.stringify(emptyList));
  }

  const convertedResponse = useMemo(() => {
    const output: IPolaroidFilm[] = [];
    console.log('fdsaf', data);
    if (!data || !data[0]) return [];
    (data as IPageableResponse[])?.forEach(page =>
      (page.content as IPolaroidFilm[]).forEach(element =>
        output.push(element),
      ),
    );
    return output;
  }, [data]);

  return (
    <div className="bg-background  py-3">
      <SelectSearch />
      {/* <button type="button" onClick={() => fetchPostRefreshToken()}>
        test
      </button> */}
      <TodayYum
        closeModal={() => setIsShowModal(false)}
        openModal={() => setIsShowModal(true)}
        isShowModal={isShowModal}
      />
      <SortCriteria />
      {!isShowModal && (
        <PolaroidList polaroidList={convertedResponse} setRef={setRef} />
      )}
    </div>
  );
}

export default MainPage;
