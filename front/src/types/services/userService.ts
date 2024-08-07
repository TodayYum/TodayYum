export interface ISignUpRequest {
  email: string;
  nickname: string;
  password: string;
}

export interface IPostCodeRequest {
  email: string;
  code: string;
}

export interface ISigninRequest {
  email: string;
  password: string;
}

export interface IGetFollowingListRequest {
  pageParam: number;
  content: string;
}

export interface IResetPassword {
  email: string;
  password: string;
}
