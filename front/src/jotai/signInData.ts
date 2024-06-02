/**
 * 로그인 했을 때 저장되는 유저 정보
 */

import { atom, useAtom } from 'jotai';
import { SignInData } from '../types/jotai/sginInData.types';

const signInDataAtom = atom<SignInData>({
  memberId: '',
  //   profile: '',
  nickname: '',
  token: '',
  //   email: '',
});

const useSignInData = atom(
  get => {
    const status = get(signInDataAtom);
    if (status.memberId === '') {
      status.memberId = localStorage.getItem('memberId') ?? '';
    }
    return status;
  },
  (get, set, input: SignInData) => {
    console.log('before', signInDataAtom, 'after', input);
    const newData = { ...get(signInDataAtom) };
    Object.entries(input).forEach(([keyName, value]) => {
      if (value) {
        newData[keyName] = value;
      }
    });
    // newData.token = input.token;
    set(signInDataAtom, newData);
  },
);

const useSignInDataAtom = () => useAtom(useSignInData);
export default useSignInDataAtom;
