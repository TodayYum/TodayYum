/**
 * userService : 회원가입, 로그인 아웃, 비밀번호 관리 등 유저 데이터 관련 API 로직
 */
import axios from 'axios';
import {
  IGetFollowingListRequest,
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

/**
 * 코드 검증
 * @param request
 * @returns
 */
export const fetchPostEmailCodeCheck = async (request: IPostCodeRequest) => {
  // 경로 문제 해결해야 함
  const url = `${API_URL}/api/auth/verify/verification-code`;

  const bodyData = new FormData();
  bodyData.append('email', request.email);
  bodyData.append('code', request.code);

  const response = await axios.post(url, bodyData);
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

  const bodyData = new FormData();
  bodyData.append('email', request.email);
  bodyData.append('nickname', request.nickname);
  bodyData.append('password', request.password);

  try {
    const response = await axios.post(url, bodyData);
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

  const bodyData = new FormData();
  bodyData.append('email', request.email);
  bodyData.append('password', request.password);

  const response = await axios.post(url, bodyData, {
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
  localStorage.setItem('nickname', response.data.result.nickname);
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

/// //////////////////////////////////////// 프로필
/**
 * fetchGetUserInfo : 유저 프로필 정보에서 사용할 유저 정보 가져오기
 * @param memberId
 * @returns
 */
export const fetchGetUserInfo = async (memberId: string) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;
  const url = `${API_URL}/api/members/${memberId}`;

  const response = await axios.get(url, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
  return response.data.result;
};

/**
 * fetchPatchEditNickname : 닉네임 변경 patch 요청
 * @param nickname
 * @returns
 */
export const fetchPatchEditNickname = async (nickname: string) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;
  const url = `${API_URL}/api/members/nicknames`;

  const formData = new FormData();
  formData.append('nickname', nickname);
  const response = await axios.patch(
    url,
    { nickname },
    {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPatchEditIntroduction : 소개글 변경 patch 요청
 * @param introduction
 * @returns
 */
export const fetchPatchEditIntroduction = async (introduction: string) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;
  const url = `${API_URL}/api/members/introductions`;

  const response = await axios.patch(
    url,
    { introduction },
    {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPostEditProfileImg : 프로필 사진 갱신 post 요청
 * @param profile
 * @returns
 */
export const fetchPostEditProfileImg = async (profile: File) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/members/profiles`;

  const formData = new FormData();
  formData.append('profile', profile);

  const response = await axios.post(url, formData, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPostConfirmPassword : 비밀번호 재설정 과정에서 비밀번호 확인
 * @param password
 * @returns
 */
export const fetchPostConfirmPassword = async (password: string) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/auth/verify/password`;

  const formData = new FormData();
  formData.append('password', password);

  const response = await axios.post(url, formData, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPatchPassword : 비밀번호 재설정 과정에서 비밀번호 확인
 * @param password: 변경된 비밀번호
 * @returns
 */
export const fetchPatchPassword = async (password: string) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/members/passwords`;

  const response = await axios.patch(
    url,
    { password },
    {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchGetFollowerList : 유저를 팔로우한 리스트 요청
 * @param request
 * @returns
 */
export const fetchGetFollowerList = async (
  request: IGetFollowingListRequest,
) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/members/${request.content}/followers`;

  const response = await axios.get(url, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    params: {
      page: request.pageParam,
    },
  });

  return response.data.result;
};

/**
 * fetchGetFollowingList : 유저가 팔로잉 한 리스트 요청
 * @param request
 * @returns
 */
export const fetchGetFollowingList = async (
  request: IGetFollowingListRequest,
) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/members/${request.content}/followings`;

  console.log('리퀘ㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔ스트', url);
  const response = await axios.get(url, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    params: {
      page: request.pageParam,
    },
  });
  console.log('요청 결곽ㄱㄱㄱㄱㄱ', response);
  return response.data.result;
};