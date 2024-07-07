import { IPolaroidFilm } from './PolaroidFilm.types';

export interface IYumList {
  onClose: () => void;
  todayYummys: IPolaroidFilm[];
}

export interface IYumByCategory {
  board: IPolaroidFilm;
}
