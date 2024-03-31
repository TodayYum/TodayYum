import { ChangeEvent } from 'react';

export interface ICommentInput {
  value: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}
