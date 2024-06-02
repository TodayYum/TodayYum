export interface IAddBoard {
  images: File[];
  content: string;
  tags: string[];
  category: string | number;
  tasteScore: number;
  priceScore: number;
  moodScore: number;
  totalScore: number;
  ateAt: string | number;
  mealTime: string;
  [key: string]: any;
}

export interface IGetBoardListRequest {
  pageParam: number;
  variables?: number[];
  content?: string;
}

export interface IGetBoardListParams {
  page: number;
  category: string;
  sortBy: string;
}

export interface IGetPageableListRequest {
  pageParam: number;
  content: string;
}

export interface IPageableResponse {
  content: any[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: unknown;
  size: number;
  sort: unknown;
  totalElements: number;
  totalPages: number;
}
