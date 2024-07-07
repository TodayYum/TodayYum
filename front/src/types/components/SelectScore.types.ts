export interface ISelectScore {
  score: number[];
  setScore?: (score: number, type: number) => void;
  customCSS?: string;
}

export interface IScoreStar {
  score: number;
  type: number;
  setScore?: (scoreInput: number, type: number) => void;
}
