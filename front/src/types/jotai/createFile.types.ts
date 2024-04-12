export interface ICreateFile {
  files: File[];
  fileURL: string[];
  contents: string;
  tags: string[];
  category: number;
  score: number[];
  date: number;
  mealTime: number;
  [key: string]: any;
}

export interface IUndefinedableCreateFile {
  files?: File[];
  fileURL?: string[];
  contents?: string;
  category?: number;
  date?: number;
  tags?: string[];
  mealTime?: number;
  score?: number[];
}
