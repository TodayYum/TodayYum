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
