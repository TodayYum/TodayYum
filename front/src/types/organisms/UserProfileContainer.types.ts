export interface IProfileImg {
  profile: string | undefined;
  profileFile: File | null;
}

export interface IUserProfileContainer extends IProfileImg {
  nickname: string;
  comment: string;
  followerCount: number;
  followingCount: number;
}
