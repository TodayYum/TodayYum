export interface ICommentContainer {
  nickname: string;
  comment: string;
  isPreview?: boolean;
  setEdit?: () => void;
}
