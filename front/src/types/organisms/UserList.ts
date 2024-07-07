export interface IUserThumbnail {
  nickname: string;
  profile: string;
  comment: string;
}

export interface IUserList {
  userList: IUserThumbnail[];
  setRef: React.Dispatch<React.SetStateAction<Element | null>>;
}
