export interface ICommentList {
  onClick: () => void;
  marginTop: number;
  boardId: number;
  commentList: ICommentReponse[];
  setRef: React.Dispatch<React.SetStateAction<Element | null>>;
  refetch: Function;
}

export interface ICommentReponse {
  id: number;
  boardId: number;
  memberId: string;
  nickname: string;
  content: string;
  profile: string;
  modifiedAt: string;
}
