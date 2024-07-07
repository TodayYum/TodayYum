import { UseMutateFunction } from '@tanstack/react-query';

export interface ICommentContainer {
  commentId: number;
  nickname: string;
  comment: string;
  memberId: string;
  profile: string;
  modifiedAt: string;
  isPreview?: boolean;
  setEdit?: () => void;
  deleteComment?: UseMutateFunction<any, Error, number, unknown>;
}
