import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faMagnifyingGlass,
  faAngleLeft,
} from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import useSearchDataAtom from '../jotai/searchData';

function SearchBar() {
  const [inputText, setInputText] = useState('');
  const [, setSearchData] = useSearchDataAtom();
  const nagivate = useNavigate();

  const addRecentSearch = (word: string) => {
    const wordListString = localStorage.getItem('recentSearch');
    let wordList;
    if (!wordListString) {
      wordList = [];
    } else {
      wordList = JSON.parse(wordListString);
    }
    wordList.push(word);
    localStorage.setItem('recentSearch', JSON.stringify(wordList));
  };

  return (
    <div className="flex bg-white items-center px-[15px] py-3 gap-5">
      <FontAwesomeIcon icon={faAngleLeft} onClick={() => nagivate(-1)} />
      <input
        type="text"
        placeholder="검색하기"
        value={inputText}
        onChange={e => setInputText(e.target.value)}
        className="focus:outline-none border-b-[1px] border-black w-full text-gray-dark pl-1 text-base"
      />
      <FontAwesomeIcon
        icon={faMagnifyingGlass}
        onClick={() => {
          addRecentSearch(inputText);
          setSearchData({ keyword: inputText });
          nagivate('/search-result');
        }}
      />
    </div>
  );
}

export default SearchBar;
