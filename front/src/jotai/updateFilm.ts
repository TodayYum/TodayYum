/**
 * 게시글 수정 시 사용하는 atom
 * 이미지 관련 프로퍼티의 차이로 인해 새로 만들기로 결정
 */

import { atom, useSetAtom, useAtomValue } from 'jotai';
import {
  IBoardDetail,
  IUndefinedableUpdateFilm,
  IUpdateBoardRequest,
} from '../types/jotai/updateFilm.types';
import { CATEGORY_MAP } from '../constant/searchConstant';
import { TIME_MAP } from '../constant/createFilmConstant';

const updateFilm = atom<IUpdateBoardRequest>({
  boardId: 0,
  content: '',
  category: 0,
  mealTime: 0,
  tasteScore: 0,
  priceScore: 0,
  moodScore: 0,
  totalScore: 0,
  ateAt: '',
  tags: [],
  removedTags: [],
});

const initUpdateFilmAtom = atom(null, (get, set, inputData: IBoardDetail) => {
  const newFilmData: IUpdateBoardRequest = { ...get(updateFilm) };
  Object.entries(newFilmData).forEach(entry => {
    const [keyName, value] = entry;
    if (typeof value === typeof inputData[keyName]) {
      newFilmData[keyName] = inputData[keyName];
    }
  });
  newFilmData.boardId = inputData.id;
  newFilmData.category = CATEGORY_MAP[inputData.category];
  newFilmData.mealTime = TIME_MAP[inputData.mealTime];
  // 기존의 태그목록과 비교를 위해 removedTags 에 저장해 놓고 비교
  newFilmData.removedTags = [...inputData.tags];

  set(updateFilm, newFilmData);
});

const updateFilmAtom = atom(
  get => get(updateFilm),
  (get, set, inputData: IUndefinedableUpdateFilm) => {
    const prevData = get(updateFilm);
    const newSearchData = { ...prevData };
    Object.entries(inputData).forEach(entry => {
      const [keyName, value] = entry;
      if (value !== undefined) {
        newSearchData[keyName] = value;
      }
    });
    set(updateFilm, newSearchData);
  },
);

export const useInitFilmAtom = () => useSetAtom(initUpdateFilmAtom);
export const useUpdateFilmAtom = () => useSetAtom(updateFilmAtom);
export const useUpdateFilmValueAtom = () => useAtomValue(updateFilmAtom);
