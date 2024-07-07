export interface ICreateFilm {
  images: File[];
  imagesURL: string[];
  content: string;
  tags: string[];
  category: number;
  score: number[];
  ateAt: number;
  mealTime: number;
  [key: string]: any;
}

export interface IUndefinedableCreateFilm {
  images?: File[];
  imagesURL?: string[];
  content?: string;
  category?: number;
  ateAt?: number;
  tags?: string[];
  mealTime?: number;
  score?: number[];
}
