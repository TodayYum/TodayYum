import { ChangeEvent } from 'react';

export interface ICommentInput {
  value: string;
  boardId: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  refetch: Function;
}
