import { IPolaroidFilm } from './PolaroidFilm.types';

export interface IYumList {
  onClose: () => void;
}

export interface IYumByCategory {
  title: string;
  board: IPolaroidFilm;
}
