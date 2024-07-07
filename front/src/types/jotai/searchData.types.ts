export interface ISearchData {
  keyword: string;
  category: boolean[];
  sort: number;
  tab: number;
  [key: string]: any;
}

export interface IUndefindableSearchData {
  keyword?: string;
  category?: boolean[];
  sort?: number;
  tab?: number;
  [key: string]: any;
}
