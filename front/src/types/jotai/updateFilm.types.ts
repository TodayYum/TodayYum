export interface IBoardDetail {
  id: number;
  content: string;
  memberId: string;
  category: string;
  mealTime: string;
  tasteScore: number;
  priceScore: number;
  moodScore: number;
  totalScore: number;
  ateAt: string;
  yummyCount: number;
  images: string[];
  profile: string;
  nickname: string;
  comment: string;
  commentWriter: string;
  tags: string[];
  yummy: false;
  [key: string]: any;
}

export interface IUpdateBoardRequest {
  boardId: number;
  content: string;
  category: number;
  mealTime: number;
  tasteScore: number;
  priceScore: number;
  moodScore: number;
  totalScore: number;
  ateAt: string;
  tags: string[];
  removedTags: string[];
  [key: string]: any;
}
export interface IUpdateBoardRequestBody {
  boardId: number;
  content: string;
  category: string | number;
  mealTime: number | string;
  tasteScore: number;
  priceScore: number;
  moodScore: number;
  totalScore: number;
  ateAt: string;
  tags: string[];
  removedTags: string[];
  [key: string]: any;
}

export interface IUndefinedableUpdateFilm {
  content?: string;
  category?: number;
  mealTime?: number;
  tasteScore?: number;
  priceScore?: number;
  moodScore?: number;
  totalScore?: number;
  ateAt?: string;
  tags?: string[];
  removedTags?: string[];
}
