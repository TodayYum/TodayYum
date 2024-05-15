/**
 * MyFilmsPage
 * 글 목록 페이지
 * @returns
 */

import { useLocation } from 'react-router-dom';
// import Header from '../atoms/Header';
import { useMemo } from 'react';
import PolaroidList from '../organisms/PolaroidList';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';
import {
  fetchGetWrittenBoard,
  fetchGetYummyBoard,
} from '../services/boardService';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import { IPageableResponse } from '../types/services/boardService';
// import { IMyFilmsPage } from '../types/pages/MyFilmsPage.types';

function MyFilmsPage() {
  const location = useLocation();
  // 여기서 작성글 조회 or 야미 글 조회
  const { type, memberId } = location.state;
  const [, setRef, data] = useInfiniteQueryProduct(
    {
      constant: type === 'written' ? 'writtenBoardList' : 'yummyBoardList',
      variables: [],
      stringVariables: [memberId],
    },
    () => (type === 'written' ? fetchGetWrittenBoard : fetchGetYummyBoard),
    {},
  );

  const product: IPolaroidFilm[] = useMemo(() => {
    const output: IPolaroidFilm[] = [];
    (data as IPageableResponse[])?.forEach(page =>
      (page.content as IPolaroidFilm[]).forEach(element =>
        output.push(element),
      ),
    );
    return output;
  }, [data]);

  return (
    <div>
      <PolaroidList polaroidList={product} setRef={setRef} />
    </div>
  );
}

export default MyFilmsPage;
