import { atom, useAtomValue, useSetAtom } from 'jotai';
import {
  IProfileImg,
  IUserProfile,
} from '../types/organisms/UserProfileContainer.types';

const userProfile = atom<IUserProfile>({
  nickname: '',
  introduction: '',
  profile: '/default.png',
  profileFile: null,
  followerCount: 10000,
  followingCount: 1,
  role: '',
  email: '',
  memberId: '',
  following: false,
});

export const userProfileAtom = () => useAtomValue(userProfile);

const setCommentAtom = atom(null, (get, set, comment: string) => {
  set(userProfile, { ...get(userProfile), introduction: comment });
});

const setNicknameAtom = atom(null, (get, set, nickname: string) => {
  set(userProfile, { ...get(userProfile), nickname });
});

const setProfileImgAtom = atom(null, (get, set, profileImg: IProfileImg) => {
  set(userProfile, {
    ...get(userProfile),
    profile: profileImg.profile ?? '/default.png',
    profileFile: profileImg.profileFile,
  });
});

const setProfileAtom = atom(null, (get, set, userInfo: IUserProfile) => {
  set(userProfile, {
    ...get(userProfile),
    nickname: userInfo.nickname,
    introduction: userInfo.introduction,
    profile: userInfo.profile,
    followerCount: userInfo.followerCount,
    followingCount: userInfo.followingCount,
    role: userInfo.role,
    email: userInfo.email,
    memberId: userInfo.memberId,
    following: userInfo.following,
  });
});

export const useSetCommentAtom = () => useSetAtom(setCommentAtom);
export const useSetNicknameAtom = () => useSetAtom(setNicknameAtom);
export const useSetProfileImgAtom = () => useSetAtom(setProfileImgAtom);
export const useSetProfileAtom = () => useSetAtom(setProfileAtom);
