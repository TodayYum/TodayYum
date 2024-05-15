export interface ICreateFilm {
  images: File[];
  imagesURL: string[];
  content: string;
  tags: string[];
  category: number;
  score: number[];
  ateAt: string;
  mealTime: number;
  [key: string]: any;
}

export interface IUndefinedableCreateFilm {
  images?: File[];
  imagesURL?: string[];
  content?: string;
  category?: number;
  ateAt?: string;
  tags?: string[];
  mealTime?: number;
  score?: number[];
}
