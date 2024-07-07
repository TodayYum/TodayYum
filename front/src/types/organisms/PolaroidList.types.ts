import { IPolaroidFilm } from './PolaroidFilm.types';

export interface IPolaroidList {
  polaroidList: IPolaroidFilm[];
  setRef?: React.Dispatch<React.SetStateAction<Element | null>>;
}
