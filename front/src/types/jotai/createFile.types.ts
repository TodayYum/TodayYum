export interface ICreateFile {
  files: File[];
  contents: string;
  tags: string[];
  score: number[];
  [key: string]: any;
}

export interface IUndefinedableCreateFile {
  files?: File[];
  contents?: string;
  tags?: string[];
  score?: number[];
}
