import { RefObject } from 'react';

export interface IDetailPageCarousel {
  imgs: string[];
  customCSS?: string;
  divRef?: RefObject<HTMLDivElement>;
  imgWidth?: number;
}
