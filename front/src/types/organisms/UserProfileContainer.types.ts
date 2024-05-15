export interface IProfileImg {
  profile: string | undefined;
  profileFile: File | null;
}

export interface IUserProfileContainer extends IProfileImg {
  nickname: string;
  memberId: string;
  profile: string;
  role: string;
  following: boolean;
  introduction: string;
  followerCount: number;
  followingCount: number;
  email: string;
}
