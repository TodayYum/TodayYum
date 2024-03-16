/**
 * RecentSearch : 최근 검색어를 담아두는 organism
 * onClick으로 보내는 함수는 front 목록에서 제거 및 백API로 제거 요청을 해야 한다.
 */

import { useState } from 'react';
import DeletableChip from '../atoms/DeletableChip';
import {
  fetchDeleteSearchWord,
  fetchRequestSearch,
} from '../services/SearchService';
import useSearchDataAtom from '../jotai/searchData';

const TEST_DUMMY: { dataId: string; text: string }[] = [
  { dataId: '0', text: '테스트0' },
  { dataId: '1', text: '테스트1' },
  { dataId: '2', text: '테스트2' },
  { dataId: '3', text: '테스트3fffffffff' },
  { dataId: '4', text: '테스트4' },
  { dataId: '5', text: '테스트5' },
];
// 페이지 진입 시 목록 받아오는 요청
// 목록을 어디다 저장할 것인가 - 여기 vs jotai 근데 이 데이터는 여기 말고 쓸데가 없음. jotai로 안보내도 됨.

function RecentSearchPage() {
  const [searchWords, setSearchWords] =
    useState<{ dataId: string; text: string }[]>(TEST_DUMMY);
  const [, setSearchWord] = useSearchDataAtom();

  const deleteSearchWord = (id: string) => {
    const deleteData = async () => {
      // 삭제에 대한 API 요청 보내기
      try {
        // 삭제 성공하면 삭제한 것의 id를 response로? 아니면 메세지만 response로?
        // 삭제한 id가 response로 온다고 가정,
        const response = await fetchDeleteSearchWord(id);
        const targetIdx = searchWords.findIndex(
          element => element.dataId === id,
        );
        const copyOfSearchWords = JSON.parse(JSON.stringify(searchWords));
        copyOfSearchWords.splice(targetIdx, 1);
        setSearchWords(copyOfSearchWords);
        console.log(response);
      } catch {
        console.log('error');
      }
    };

    deleteData();
    //
  };

  const handleSelectButton = (word: string) => {
    // 검색어 갱신하고, 이걸 기반으로 API 요청
    setSearchWord({ keyword: word, sort: 0, tab: 0 });
    const requsetSearch = async () => {
      try {
        fetchRequestSearch({ keyword: word, sort: 0, tab: 0 });
      } catch {
        console.log('error');
      }
    };
    requsetSearch();
  };
  return (
    <div className="bg-white my-3 mx-[30px] rounded">
      <p className="base-bold ml-4 pt-4">최근 검색어</p>
      <div className="flex gap-4 p-3 flex-wrap">
        {searchWords.map(element => (
          <DeletableChip
            dataId={element.dataId}
            text={element.text}
            onSelectClick={() => handleSelectButton(element.text)}
            deleteSearchWord={() => deleteSearchWord(element.dataId)}
            key={element.dataId}
          />
        ))}
      </div>
    </div>
  );
}

export default RecentSearchPage;
