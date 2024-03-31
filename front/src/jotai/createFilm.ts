/**
 * 게시글 등록 시 사용하는 atom
 */

import { atom, useAtom } from 'jotai';
import {
  ICreateFile,
  IUndefinedableCreateFile,
} from '../types/jotai/createFile.types';

const createFilm = atom<ICreateFile>({
  files: [],
  contents: '',
  tags: [],
  score: [0, 0, 0],
});

const createFilmAtom = atom(
  get => get(createFilm),
  (get, set, inputData: IUndefinedableCreateFile) => {
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
