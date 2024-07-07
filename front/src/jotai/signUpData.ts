/**
 * 회원가입에서 관리하는 정보
 * signUpDataAtom : initialAtom
 * setEmail : 이메일 갱신 set
 * setCode : 인증코드 갱신
 * plusSignUpLevel : signUp 단계 상향
 * minusSignUpLevel : signUp 단계 하향
 */

import { atom, useAtomValue, useSetAtom } from 'jotai';
import { ISignUpPage } from '../types/pages/SignUpPage.types';

const signUpDataAtom = atom<ISignUpPage>({
  email: '',
  password: '',
  confirmPassword: '',
  code: '',
  nickname: '',
  signUpLevel: 0,
});

const initsignUpDataAtom = atom(null, (get, set) => {
  set(signUpDataAtom, {
    email: '',
    password: '',
    confirmPassword: '',
    code: '',
    nickname: '',
    signUpLevel: 0,
  });
});

// setEmail
const setEmail = atom(null, (get, set, email: string) => {
  const signUpStatus = {
    ...get(signUpDataAtom),
    email,
  };
  set(signUpDataAtom, signUpStatus);
});
// setCode
const setCode = atom(null, (get, set, code: string) => {
  const signUpStatus = {
    ...get(signUpDataAtom),
    code,
  };
  set(signUpDataAtom, signUpStatus);
});

// signUpLevel 증가
const plusSignUpLevel = atom(null, (get, set) => {
  const prev = { ...get(signUpDataAtom) };
  const signUpStatus = {
    ...prev,
    signUpLevel: prev.signUpLevel + 1,
  };
  set(signUpDataAtom, signUpStatus);
});

// signUpLevel 감소
const minusSignUpLevel = atom(null, (get, set) => {
  const prev = { ...get(signUpDataAtom) };
  const signUpStatus = {
    ...prev,
    signUpLevel: prev.signUpLevel - 1,
  };
  set(signUpDataAtom, signUpStatus);
});

// password 등록
const setPassword = atom(null, (get, set, password: string) => {
  const signUpStatus = {
    ...get(signUpDataAtom),
    password,
  };
  set(signUpDataAtom, signUpStatus);
});

// 비밀번호 확인
const setConfirmPassword = atom(null, (get, set, confirmPassword: string) => {
  const signUpStatus = {
    ...get(signUpDataAtom),
    confirmPassword,
  };
  set(signUpDataAtom, signUpStatus);
});

// nickname 등록
const setNickname = atom(null, (get, set, nickname: string) => {
  const signUpStatus = {
    ...get(signUpDataAtom),
    nickname,
  };
  set(signUpDataAtom, signUpStatus);
});

export const useSetEmailAtom = () => useSetAtom(setEmail);
export const useSetCodeAtom = () => useSetAtom(setCode);
export const usePlusSignUpLevelAtom = () => useSetAtom(plusSignUpLevel);
export const useMinusSignUpLevelAtom = () => useSetAtom(minusSignUpLevel);
export const useSignUpDataAtom = () => useAtomValue(signUpDataAtom);
export const useSetPasswordAtom = () => useSetAtom(setPassword);
export const useSetConfirmPasswordAtom = () => useSetAtom(setConfirmPassword);
export const useSetNicknameAtom = () => useSetAtom(setNickname);
export const useInitsignUpDataAtom = () => useSetAtom(initsignUpDataAtom);

// 고민 : 여러가지 방법?
// 1. 지금과 같은 방법
// 2. setAtom 분리하지 말고 통합 setAtom하나만 놓은 다음에, organism 코드에서 각각 알아서 넣기 (근데 이건 메인 코드가 길어지니까 내가 불호)
// 3. 아니면 저 통합 atom 말고 initialAtom 자체를 분리하는거임 (atom 파일의 분리)
// 1이 걱정되는건 atom 파일 코드 길이가 너무 커지는거? 나중에 성능 테스트할때 로딩 속도를 보면 좋을듯
