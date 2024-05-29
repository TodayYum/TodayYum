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
import { fetchPostRefreshToken } from '../services/userService';

function MainPage() {
  const [isShowModal, setIsShowModal] = useState(false);
  const [, setRef, data] = useInfiniteQueryProduct(
    { constant: 'boardList', variables: [] },
    () => fetchGetBoardList,
    {},
  );

  const convertedResponse = useMemo(() => {
    const output: IPolaroidFilm[] = [];
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
      <button
        type="button"
        onClick={() => {
          fetchPostRefreshToken();
        }}
      >
        테스트
      </button>
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
