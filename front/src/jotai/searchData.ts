/**
 * 검색 창에서 활용하는 정보
 * 검색어, 정렬순, 카테고리, 선택한 탭
 */

import { atom, useAtom } from 'jotai';
import {
  ISearchData,
  IUndefindableSearchData,
} from '../types/jotai/searchData.types';

const searchData = atom<ISearchData>({
  keyword: '',
  category: Array(10).fill(false),
  sort: 0,
  tab: 0,
});

const searchDataAtom = atom(
  get => get(searchData),
  (get, set, inputData: IUndefindableSearchData) => {
    const prevData = get(searchData);
    const newSearchData = { ...prevData };
    Object.entries(inputData).forEach(entry => {
      const [keyName, value] = entry;
      if (value !== undefined) {
        newSearchData[keyName] = value;
      }
    });
    set(searchData, newSearchData);
  },
);

const useSearchDataAtom = () => useAtom(searchDataAtom);
export default useSearchDataAtom;
