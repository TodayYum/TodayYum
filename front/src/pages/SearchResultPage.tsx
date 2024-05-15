/**
 * SearchPage : 검색 버튼 활성화시 나오는 페이지
 * 검색어 입력 전 : 최근 검색어 게시
 * 검색어 입력 및 엔터(돋보기 클릭) 후 : 3개 탭 및 결과 활성화
 * @returns
 */

import { useMemo } from 'react';
import SearchTab from '../organisms/SearchTab';
import useSearchDataAtom from '../jotai/searchData';
import UserList from '../organisms/UserList';
import PolaroidList from '../organisms/PolaroidList';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import { IUserThumbnail } from '../types/organisms/UserList';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';
import { fetchGetSearchBoard } from '../services/boardService';
import { fetchCheckEmailDuplicate } from '../services/userService';
import { IPageableResponse } from '../types/services/boardService';
// const TAB_TAG = 0;
// const TAB_REGION = 1;
const TAB_ACCOUNT = 2;

function SearchResultPage() {
  const [{ keyword, tab }] = useSearchDataAtom();

  const [, setRef, data] = useInfiniteQueryProduct(
    {
      constant: tab === TAB_ACCOUNT ? 'accountList' : 'boardList',
      variables: [],
      stringVariables: [keyword],
    },
    () =>
      tab === TAB_ACCOUNT ? fetchCheckEmailDuplicate : fetchGetSearchBoard,
    {},
  );
  // }

  const product: IPolaroidFilm[] | IUserThumbnail[] = useMemo(() => {
    if (tab === TAB_ACCOUNT) {
      const output: IUserThumbnail[] = [];
      (data as IPageableResponse[])?.forEach(page =>
        (page.content as IUserThumbnail[]).forEach(element =>
          output.push(element),
        ),
      );
      return output;
    }
    const output: IPolaroidFilm[] = [];
    (data as IPageableResponse[])?.forEach(page =>
      (page.content as IPolaroidFilm[]).forEach(element =>
        output.push(element),
      ),
    );
    return output;
  }, [data, tab]);

  return (
    <div>
      <SearchTab />
      {tab === TAB_ACCOUNT ? (
        <UserList userList={product as IUserThumbnail[]} setRef={setRef} />
      ) : (
        <PolaroidList
          polaroidList={product as IPolaroidFilm[]}
          setRef={setRef}
        />
      )}
    </div>
  );
}

export default SearchResultPage;
