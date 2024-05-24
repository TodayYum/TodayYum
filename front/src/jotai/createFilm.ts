/**
 * 게시글 등록 시 사용하는 atom
 */

import { atom, useAtom } from 'jotai';
import {
  ICreateFilm,
  IUndefinedableCreateFilm,
} from '../types/jotai/createFile.types';

const createFilm = atom<ICreateFilm>({
  images: [],
  imagesURL: [],
  ateAt: 0,
  content: '',
  category: -1,
  tags: [],
  score: [0, 0, 0],
  mealTime: 3,
});

const createFilmAtom = atom(
  get => get(createFilm),
  (get, set, inputData: IUndefinedableCreateFilm) => {
    const prevData = get(createFilm);
    const newSearchData = { ...prevData };
    Object.entries(inputData).forEach(entry => {
      const [keyName, value] = entry;
      if (value !== undefined) {
        newSearchData[keyName] = value;
      }
    });
    set(createFilm, newSearchData);
  },
);

const useCreateFilmAtom = () => useAtom(createFilmAtom);
export default useCreateFilmAtom;
