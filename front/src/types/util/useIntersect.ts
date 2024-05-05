// IntersectionObserverInit 타입을 정의합니다.
export interface IIntersectionObserverInit {
  root?: Element | null;
  rootMargin?: string;
  threshold?: number | number[];
  // 필요에 따라 더 많은 프로퍼티들을 추가할 수 있습니다.
}

export interface IPageInfo {
  pageNumber: number;
  hasNext: boolean;
}
