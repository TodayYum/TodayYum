/**
 * RecentSearch : 최근 검색어를 담아두는 organism
 * onClick으로 보내는 함수는 front 목록에서 제거 및 백API로 제거 요청을 해야 한다.
 */

import { useState } from 'react';
import DeletableChip from '../atoms/DeletableChip';
import useSearchDataAtom from '../jotai/searchData';

// const TEST_DUMMY: { dataId: string; text: string }[] = [
//   { dataId: '0', text: '테스트0' },
//   { dataId: '1', text: '테스트1' },
//   { dataId: '2', text: '테스트2' },
//   { dataId: '3', text: '테스트3fffffffff' },
//   { dataId: '4', text: '테스트4' },
//   { dataId: '5', text: '테스트5' },
// ];
// 페이지 진입 시 목록 받아오는 요청
// 목록을 어디다 저장할 것인가 - 여기 vs jotai 근데 이 데이터는 여기 말고 쓸데가 없음. jotai로 안보내도 됨.

function RecentSearchPage() {
  const [searchWords, setSearchWords] = useState<string[]>(
    JSON.parse(localStorage.getItem('recentSearch') ?? ''),
  );
  const [, setSearchWord] = useSearchDataAtom();

  const deleteSearchWord = (id: number) => {
    const copyOfSearchWords = [...searchWords];
    copyOfSearchWords.splice(id, 1);
    localStorage.setItem('recentSearch', JSON.stringify(copyOfSearchWords));
    setSearchWords(copyOfSearchWords);
  };

  const handleSelectButton = (word: string) => {
    setSearchWord({ keyword: word, sort: 0, tab: 0 });
  };

  return (
    <div className="bg-white my-3 mx-[30px] rounded">
      <p className="base-bold ml-4 pt-4">최근 검색어</p>
      <div className="flex gap-4 p-3 flex-wrap">
        {searchWords.map((element, idx) => (
          <DeletableChip
            dataId={String(idx)}
            text={element}
            onSelectClick={() => handleSelectButton(element)}
            deleteSearchWord={() => deleteSearchWord(idx)}
            key={element}
          />
        ))}
      </div>
    </div>
  );
}

export default RecentSearchPage;
