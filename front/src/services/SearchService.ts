/**
 * 검색 관련 API 요청 다루는 파일
 */

import { IUndefindableSearchData } from '../types/jotai/searchData.types';

export const fetchDeleteSearchWord = async (id: string) => {
  console.log(id);
};

export const fetchRequestSearch = async (input: IUndefindableSearchData) => {
  console.log(input);
};
