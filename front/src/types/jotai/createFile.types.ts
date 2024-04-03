export interface ICreateFile {
  files: File[];
  fileURL: string[];
  contents: string;
  tags: string[];
  category: boolean[];
  score: number[];
  [key: string]: any;
}

export interface IUndefinedableCreateFile {
  files?: File[];
  fileURL?: string[];
  contents?: string;
  category?: boolean[];
  tags?: string[];
  score?: number[];
}
