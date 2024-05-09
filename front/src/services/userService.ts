/**
 * userService : 회원가입, 로그인 아웃, 비밀번호 관리 등 유저 데이터 관련 API 로직
 */
import axios from 'axios';
import {
  IPostCodeRequest,
  ISignUpRequest,
  ISigninRequest,
} from '../types/services/userService';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

export const fetchFindPassword = () => {};

// 이메일 중복 검사 요청 API
export const fetchCheckEmailDuplicate = async (email: string) => {
  if (email.length === 0) return { input: email, result: -1 };

  const url = `${API_URL}/api/members/emails/validations`;

  try {
    const response = await axios.get(url, {
      params: { email },
    });
    return { input: email, result: response.data };
  } catch (err) {
    return { input: email, result: false };
  }
};

// 이메일 인증 코드 생성 요청
export const fetchPostEmailCode = async (email: string) => {
  const url = `${API_URL}/api/auth/verification-code`;

  const params = new URLSearchParams();
  params.append('email', email);

  try {
    const response = await axios.post(url, null, { params });
    return response.data;
  } catch {
    console.log('이메일 인증 코드 생성 실패');
    return false;
  }
};

// 이메일 인증 코드 확인 요청
export const fetchPostEmailCodeCheck = async (request: IPostCodeRequest) => {
  const url = `${API_URL}/api/auth/verify-verification`;

  const params = new URLSearchParams();
  params.append('email', request.email);
  params.append('code', request.code);

  const response = await axios.post(url, null, {
    params,
  });
  return response.data;
};

// 닉네임 중복 검사 get 요청
export const fetchGetNicknameDuplicate = async (nickname: string) => {
  if (nickname.length === 0) return -1;

  const url = `${API_URL}/api/members/nicknames/validations`;

  try {
    const response = await axios.get(url, {
      params: { nickname },
    });
    return response.data.message.length;
  } catch (err) {
    console.log('닉 검증 실패', err);
    return 0;
  }
};

/**
 * fetchPostSignUp : 회원가입 post 요청
 */
export const fetchPostSignUp = async (request: ISignUpRequest) => {
  const url = `${API_URL}/api/members`;

  const params = new URLSearchParams();
  params.append('email', request.email);
  params.append('nickname', request.nickname);
  params.append('password', request.password);

  try {
    const response = await axios.post(url, null, { params });
    return response.data;
  } catch (err) {
    return err;
  }
};

/**
 * fetchPostSignin : 로그인 요청 axios 함수
 * 로그인 성공 시 localStroage에 accessToken 저장
 */
export const fetchPostSignin = async (request: ISigninRequest) => {
  const url = `${API_URL}/api/auth/login`;

  const params = new URLSearchParams();
  params.append('email', request.email);
  params.append('password', request.password);

  const response = await axios.post(url, null, {
    params,
    withCredentials: true,
  });

  if (
    response.headers.getAuthorization &&
    typeof response.headers.getAuthorization === 'function'
  ) {
    const accessToken = response.headers
      .getAuthorization()
      ?.toString()
      .split(' ')[1];
    localStorage.setItem('token', accessToken ?? '');

    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
  }

  localStorage.setItem('memberId', response.data.result.memberId);
  return response.data;
};

/**
 * fetchPostRefreshToken : 토큰 재발급후 localStorage에 저장하는 함수
 */
export const fetchPostRefreshToken = async () => {
  // 브라우저에 저장된 memberId 날아갔을 경우 함수 종료
  if (!localStorage.getItem('memberId')) return false;

  const url = `${API_URL}/api/auth/refresh`;

  const params = new URLSearchParams();
  params.append('memberId', localStorage.getItem('memberId') ?? '');

  const response = await axios.post(url, null, {
    params,
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchPostRefreshToken : 토큰 재발급 인터셉터 등록
 * 로그인 성공 시 localStroage에 accessToken 저장
 */
export const setRefreshInterceptor = () => {
  axios.interceptors.response.use(
    res => res,
    async error => {
      // NOTICE! 토큰 만료 시, 에러가 어떻게 오는지 확인해야 함
      if (error.response.status === 401) {
        await fetchPostRefreshToken();
      }
      console.log('인터셉터가 감지한 에러', error);
    },
  );
};

/**
 * fetchPostSignOut : 로그아웃 함수
 */
export const fetchPostSignOut = async () => {
  // 브라우저에 저장된 memberId 날아갔을 경우 함수 종료
  if (!localStorage.getItem('memberId')) return false;

  const url = `${API_URL}/api/auth/logout`;

  const params = new URLSearchParams();
  params.append('memberId', localStorage.getItem('memberId') ?? '');

  const response = await axios.post(url, null, {
    withCredentials: true,
  });
  console.log('로그아웃 결과', response);
  return response.data;
};

/**
 * fetchPostResetPassword : 비밀번호 잊어버렸을 경우 비밀번호 재설정 요청
 * 현재 API 미구현이라 보류
 * @param request
 * @returns
 */
export const fetchPostResetPassword = async (request: string) => {
  // 브라우저에 저장된 memberId 날아갔을 경우 함수 종료
  if (!localStorage.getItem('memberId')) return false;

  const url = `${API_URL}/api/members/password`;

  const params = new URLSearchParams();
  params.append('password', request);

  const response = await axios.post(url, request);
  console.log('로그아웃 결과', response);
  return response.data;
};
