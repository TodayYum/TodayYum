/**
 * useIntersect : 리스트 담아오는 API 요청만 onIntersect로 받아오고,
 * intersectionObserver와 hasNext, pageNumber는 사용자 정의 hook 내부에서 처리하는 함수
 * @param onIntersect : API 요청 받아오는 함수
 * options : observer 속성 (선택)
 */

import { useState, useEffect, useCallback } from 'react';
import {
  IIntersectionObserverInit,
  IPageInfo,
} from '../types/util/useIntersect';

const defaultOptions: IIntersectionObserverInit = {
  root: null,
  threshold: 1,
  rootMargin: '0px',
};

// onIntersect 콜백 함수의 타입을 정의합니다.
type OnIntersectCallback = (
  entry: IntersectionObserverEntry,
  observer: IntersectionObserver,
) => Promise<boolean>;

const useIntersect = (
  onIntersect: OnIntersectCallback,
  options: IIntersectionObserverInit,
) => {
  const [ref, setRef] = useState<Element | null>(null);
  const [pageInfo, setPageInfo] = useState<IPageInfo>({
    hasNext: true,
    pageNumber: 0,
  });
  const checkIntersect = useCallback(
    async (
      [entry]: IntersectionObserverEntry[],
      observer: IntersectionObserver,
    ) => {
      if (!entry.isIntersecting) return;

      // 불러올게 없을때
      if (!pageInfo.hasNext) return;

      // onIntersect는 API 요청을 통해 list를 갱신하고, hasNext여부를 boolean으로 전달해야 함
      const hasNextValue = await onIntersect(entry, observer);

      // hasNext와 pageNumber 처리
      setPageInfo({
        hasNext: hasNextValue,
        pageNumber: pageInfo.pageNumber + 1,
      });
    },
    [onIntersect],
  );

  useEffect(() => {
    let observer: IntersectionObserver | undefined;
    if (ref) {
      observer = new IntersectionObserver(checkIntersect, {
        ...defaultOptions,
        ...options,
      });
      observer.observe(ref);
    }
    return () => observer && observer.disconnect();
  }, [
    ref,
    options.root,
    options.threshold,
    options.rootMargin,
    checkIntersect,
  ]);

  return [ref, setRef] as const;
};

export default useIntersect;
