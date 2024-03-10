export interface ISearchData {
  keyword: string;
  category: number;
  sort: number;
  tab: number;
  [key: string]: any;
}

export interface IUndefindableSearchData {
  keyword?: string;
  category?: number;
  sort?: number;
  tab?: number;
  [key: string]: any;
}
