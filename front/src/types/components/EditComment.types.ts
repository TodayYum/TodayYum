export interface IEditComment {
  commentId: number;
  exitEdit: () => void;
  comment: string;
  profile: string;
  nickname: string;
  editComment: Function;
}
