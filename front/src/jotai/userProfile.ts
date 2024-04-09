import { atom, useAtomValue, useSetAtom } from 'jotai';
import {
  IProfileImg,
  IUserProfileContainer,
} from '../types/organisms/UserProfileContainer.types';

const userProfile = atom<IUserProfileContainer>({
  nickname: 'yonggkimm',
  comment: 'ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ',
  profile: undefined,
  profileFile: null,
  followerCount: 10000,
  followingCount: 1,
});

export const userProfileAtom = () => useAtomValue(userProfile);

const setCommentAtom = atom(null, (get, set, comment: string) => {
  set(userProfile, { ...get(userProfile), comment });
});

const setNicknameAtom = atom(null, (get, set, nickname: string) => {
  set(userProfile, { ...get(userProfile), nickname });
});

const setProfileImgAtom = atom(null, (get, set, profileImg: IProfileImg) => {
  set(userProfile, {
    ...get(userProfile),
    profile: profileImg.profile,
    profileFile: profileImg.profileFile,
  });
});

export const useSetCommentAtom = () => useSetAtom(setCommentAtom);
export const useSetNicknameAtom = () => useSetAtom(setNicknameAtom);
export const useSetProfileImgAtom = () => useSetAtom(setProfileImgAtom);
