import axios from 'axios';
import Cookies from 'js-cookie';
import {
  IPostCodeRequest,
  ISignUpRequest,
  ISigninRequest,
} from '../types/services/userService';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

export const fetchLogin = () => {};

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

    console.log('이메일 인증 코드 생성 post 결과 확인', email, response.data);
    return response.data;
  } catch {
    console.log('이메일 인증 코드 생성 실패');
    return false;
  }
};

// 이메일 인증 코드 확인 요청
export const fetchPostEmailCodeCheck = async (request: IPostCodeRequest) => {
  // if (email.length === 0) return { input: email, result: -1 };
  console.log('리퀘 확인', request);
  const params = new URLSearchParams();
  params.append('email', request.email);
  params.append('code', request.code);
  const url = `${API_URL}/api/auth/verify-verification`;
  //   try {
  //     const response = await axios.post(url, null, {
  //       params,
  //       //   withCredentials: true,
  //       //   withXSRFToken: true,
  //     });
  //     console.log('인증 코드 확인 성공', response.data, request);
  //     return response.data;
  //   } catch (err) {
  //     console.log('인증 코드 확인 실패', err);
  //     return false;
  //   }
  const response = await axios.post(url, null, {
    params,
    //   withCredentials: true,
    //   withXSRFToken: true,
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
    //   return { input: email, result: response.data };
    console.log('닉네임 검증 결과', response.data);
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
  console.log('요청', request);
  const params = new URLSearchParams();
  params.append('email', request.email);
  params.append('nickname', request.nickname);
  params.append('password', request.password);
  try {
    const response = await axios.post(url, null, { params });

    console.log('회원가입 post 결과 확인', request, response.data);
    return response.data;
  } catch (err) {
    console.log('회원가입 실패', request, err);
    return false;
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
  if (response.headers.get && typeof response.headers.get === 'function') {
    console.log('TLqkf', response.headers.get('Set-Cookie'));
  }
  localStorage.setItem('memberId', response.data.result.memberId);
  console.log('확인', response);
  console.log('쿠확', document.cookie, Cookies);
  return response.data;
};

/**
 * fetchPostRefreshToken : 토큰 재발급후 localStorage에 저장하는 함수
 */
// 인터셉터에서 요청할 때 결국 memberID, refresh토큰 필요 memberId도 스토리지에 넣어야 함
// refresh 토큰 낮에 할때는 httpOnly true에서 안되었는데 밤에는 알아서 됨. 내일 한 번더 확인해볼 것
export const fetchPostRefreshToken = async () => {
  // 브라우저에 저장된 memberId, refreshToken 날아갔을 경우 함수 종료
  if (!localStorage.getItem('memberId')) return false;
  const params = new URLSearchParams();
  params.append('memberId', localStorage.getItem('memberId') ?? '');
  const url = `${API_URL}/api/auth/refresh`;
  const response = await axios.post(url, null, {
    params,
    withCredentials: true,
  });
  console.log('재발금 결과', response);
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
  // 브라우저에 저장된 memberId, refreshToken 날아갔을 경우 함수 종료
  if (!localStorage.getItem('memberId')) return false;
  const params = new URLSearchParams();
  params.append('memberId', localStorage.getItem('memberId') ?? '');
  const url = `${API_URL}/api/auth/logout`;
  const response = await axios.post(url, null, {
    withCredentials: true,
    // headers: {
    //   Cookie: `refreshToken=${Cookies.get('refreshToken')}`,
    // },
  });
  console.log('로그아웃 결과', response);
  return response.data;
};
