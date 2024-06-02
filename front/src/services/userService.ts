/**
 * userService : 회원가입, 로그인 아웃, 비밀번호 관리 등 유저 데이터 관련 API 로직
 */
import axios from 'axios';
import {
  IGetFollowingListRequest,
  IPostCodeRequest,
  IResetPassword,
  ISignUpRequest,
  ISigninRequest,
} from '../types/services/userService';
import EmailCheck from '../constant/enums';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

interface AxiosErr {
  response: { data: { message: string } };
}
/**
 * fetchCheckEmailDuplicate : 이메일 중복 체크 확인
 * @param email
 * @returns
 */
export const fetchCheckEmailDuplicate = async (email: string) => {
  if (email.length === 0) return { input: email, result: -1 };

  const url = `${API_URL}/api/members/emails/validations`;

  // err.response.data.message "이미 사용 중인 이메일입니다." err.message "Request failed with status code 409"
  // '유효하지 않은 이메일입니다.'  Request failed with status code 409"
  try {
    await axios.get(url, {
      params: { email },
    });

    return { input: email, result: EmailCheck.VALID };
  } catch (err) {
    switch ((err as AxiosErr).response.data.message) {
      case '이미 사용 중인 이메일입니다.':
        return { input: email, result: EmailCheck.DUPLICATE };
      case '유효하지 않은 이메일입니다.':
        return { input: email, result: EmailCheck.INVALID };
      default:
        return { input: email, result: EmailCheck.INTERNAL_ERROR };
    }
  }
};

/**
 * fetchPostEmailCodeForSignup : 회원가입 시 사용하는 인증코드 생성 요청
 * @param email
 * @returns
 */
export const fetchPostEmailCodeForSignup = async (email: string) => {
  const url = `${API_URL}/api/auth/verification-code/signUp`;

  const params = new URLSearchParams();
  params.append('email', email);

  // try {
  const response = await axios.post(url, null, { params });
  return response.data;
  // } catch {
  //   console.log('이메일 인증 코드 생성 실패');
  //   return false;
  // }
};

/**
 * fetchPostEmailCodeForResetPassword : 비밀번호 찾기에서 사용하는 인증 코드 생성 요청
 * @param email
 * @returns
 */
export const fetchPostEmailCodeForResetPassword = async (email: string) => {
  const url = `${API_URL}/api/auth/verification-code/password`;

  const params = new URLSearchParams();
  params.append('email', email);

  // try {
  const response = await axios.post(url, null, { params });
  return response.data;
  // } catch {
  //   console.log('이메일 인증 코드 생성 실패');
  //   return false;
  // }
};

/**
 * 코드 검증
 * @param request
 * @returns
 */
export const fetchPostEmailCodeCheck = async (request: IPostCodeRequest) => {
  const url = `${API_URL}/api/auth/verify/verification-code`;

  const response = await axios.post(url, {
    email: request.email,
    code: request.code,
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
  let accessToken = '';
  const response = await axios.post(url, bodyData, {
    withCredentials: true,
  });

  if (
    response.headers.getAuthorization &&
    typeof response.headers.getAuthorization === 'function'
  ) {
    accessToken =
      response.headers.getAuthorization()?.toString().split(' ')[1] ?? '';
    // localStorage.setItem('token', accessToken ?? '');

    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    response.data.result.token = accessToken;
  }

  localStorage.setItem('memberId', response.data.result.memberId);
  return response.data.result;
};

/**
 * fetchPostRefreshToken : 토큰 재발급후 localStorage에 저장하는 함수
 */
export const fetchPostRefreshToken = async (memberId: string) => {
  const url = `${API_URL}/api/auth/refresh`;

  const params = new URLSearchParams();
  params.append('memberId', memberId);
  const response = await axios.post(url, null, {
    params,
    headers: {
      Authorization: '',
    },
    withCredentials: true,
  });

  let accessToken = '';
  if (
    response.headers.getAuthorization &&
    typeof response.headers.getAuthorization === 'function'
  ) {
    accessToken =
      response.headers.getAuthorization()?.toString().split(' ')[1] ?? '';

    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    response.data.token = accessToken;
  }
  console.log('리프레시 API 호출 여부 확인', response);
  return response;
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
        await fetchPostRefreshToken('test');
      }
      console.log('인터셉터가 감지한 에러', error);
    },
  );
};

/**
 * fetchPostSignOut : 로그아웃 함수
 */
export const fetchPostSignOut = async (memberId: string) => {
  // 브라우저에 저장된 memberId 날아갔을 경우 함수 종료

  const url = `${API_URL}/api/auth/logout`;

  const params = new URLSearchParams();
  params.append('memberId', memberId);

  const response = await axios.post(url, null, {
    withCredentials: true,
  });

  localStorage.clear();
  return response.data;
};

/// //////////////////////////////////////// 프로필
/**
 * fetchGetUserInfo : 유저 프로필 정보에서 사용할 유저 정보 가져오기
 * @param memberId
 * @returns
 */
export const fetchGetUserInfo = async (memberId: string) => {
  // const accessToken = localStorage.getItem('token');
  console.log('들어와ㅏㅅ니');
  // if (!accessToken) return false;
  const url = `${API_URL}/api/members/${memberId}`;
  try {
    const response = await axios.get(url, {
      withCredentials: true,
      // headers: {
      //   Authorization: `Bearer ${accessToken}`,
      // },
    });
    return response.data;
  } catch (err) {
    console.log('테스트', err);
    return err;
  }
};

/**
 * fetchPatchEditNickname : 닉네임 변경 patch 요청
 * @param nickname
 * @returns
 */
export const fetchPatchEditNickname = async (nickname: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;
  const url = `${API_URL}/api/members/nicknames`;

  const formData = new FormData();
  formData.append('nickname', nickname);
  const response = await axios.patch(
    url,
    { nickname },
    {
      withCredentials: true,
      // headers: {
      //   Authorization: `Bearer ${accessToken}`,
      // },
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
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;
  const url = `${API_URL}/api/members/introductions`;

  const response = await axios.patch(
    url,
    { introduction },
    {
      withCredentials: true,
      // headers: {
      //   Authorization: `Bearer ${accessToken}`,
      // },
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
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/profiles`;

  const formData = new FormData();
  formData.append('profile', profile);

  const response = await axios.post(url, formData, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
  });

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPostConfirmPassword : 비밀번호 재설정 과정에서 비밀번호 확인
 * @param password
 * @returns
 */
export const fetchPostConfirmPassword = async (password: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/auth/verify/password`;

  const formData = new FormData();
  formData.append('password', password);

  const response = await axios.post(
    url,
    { password },
    {
      withCredentials: true,
      // headers: {
      //   Authorization: `Bearer ${accessToken}`,
      // },
    },
  );

  return response.data.message === '요청이 완료되었습니다.';
};

/**
 * fetchPatchPassword : 비밀번호 재설정 과정에서 비밀번호 확인
 * @param password: 변경된 비밀번호
 * @returns
 */
export const fetchPatchPassword = async (request: IResetPassword) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/passwords`;

  const response = await axios.patch(
    url,
    {
      email: request.email,
      password: request.password,
    },
    {
      withCredentials: true,
      // headers: {
      //   Authorization: `Bearer ${accessToken}`,
      // },
      headers: {
        Authorization: ``,
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
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/${request.content}/followers`;

  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
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
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/${request.content}/followings`;

  console.log('리퀘ㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔ스트', url);
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    params: {
      page: request.pageParam,
    },
  });
  console.log('요청 결곽ㄱㄱㄱㄱㄱ', response);
  return response.data.result;
};

/**
 * fetchPostAddFollow : 팔로우 등록하는 api 요청
 * @param memberId
 * @returns
 */
export const fetchPostAddFollow = async (memberId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/${memberId}/follow`;

  const response = await axios.post(url, null, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchDeleteFollow : 팔로우 취소하는 API 요청
 * @param memberId
 * @returns
 */
export const fetchDeleteFollow = async (memberId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/${memberId}/follow`;

  const response = await axios.delete(url, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchGetSearchUsers : 유저 검색 API 요청
 * @param
 * @returns
 */
export const fetchGetSearchUsers = async (request: {
  pageParam: number;
  content: string;
}) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members/search`;
  const params = new URLSearchParams();
  params.append('nickname', request.content);

  console.log('유저검색', request);
  const response = await axios.get(url, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
    params: {
      page: request.pageParam,
      nickname: request.content,
    },
  });
  return response.data.result;
};

/**
 * fetchDeleteUser : 회원탈퇴
 * @returns
 */
export const fetchDeleteUser = async () => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/members`;

  const response = await axios.delete(url, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data.result;
};
