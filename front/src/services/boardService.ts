import axios from 'axios';
import * as qs from 'qs';
import { ICreateFilm } from '../types/jotai/createFile.types';
import {
  IAddBoard,
  IGetBoardListParams,
  IGetBoardListRequest,
  IGetPageableListRequest,
  // IGetWrittenBoardListRequest,
} from '../types/services/boardService';
import { CATEGORY_LIST, SORT_LIST_EN } from '../constant/searchConstant';
import { TIME_LIST } from '../constant/createFilmConstant';
import {
  IUpdateBoardRequest,
  IUpdateBoardRequestBody,
} from '../types/jotai/updateFilm.types';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

const DEFAULT_CREATE_BOARD: IAddBoard = {
  images: [],
  content: '',
  tags: [],
  category: 0,
  tasteScore: 0,
  priceScore: 0,
  moodScore: 0,
  totalScore: 0,
  ateAt: 0,
  mealTime: '',
};

/**
 * fetchPostAddBoard : 게시글 생성 API 요청, 프론트 처리용 데이터 형식을
 * request용으로 변환시키고, formdata 형식으로 전송
 * @param request ICreateFilm
 * @returns
 */
export const fetchPostAddBoard = async (input: ICreateFilm) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards`;
  const request: IAddBoard = { ...DEFAULT_CREATE_BOARD };

  // 프론트 데이터를 백에서 다룰 데이터로 변환하는 작업. score만 프로퍼티 이름이 달라 분기 처리
  Object.entries(input).forEach(entry => {
    const [keyName, value] = entry;
    if (!Object.hasOwn(request, keyName)) {
      if (keyName === 'imagesURL') return;
      [request.tasteScore, request.priceScore, request.moodScore] = value;
      request.totalScore =
        Math.round(
          (10 * value.reduce((prev: number, curr: number) => prev + curr)) / 3,
        ) / 10;
      return;
    }
    request[keyName] = value;
  });

  // 같은 프로퍼티 이름 중, category, mealTime, ateAt은 자료형이 다르므로 그에 맞게 변경
  request.category = CATEGORY_LIST.en[input.category ?? 0];
  request.mealTime = TIME_LIST[input.mealTime ?? 0].en;
  // 프론트 데이터에서 백의 LocalDate 형식으로 변환시키기 위함
  const currentDate = new Date();
  currentDate.setDate(currentDate.getDate() - (request.ateAt as number));
  [request.ateAt] = new Date(currentDate).toISOString().split('T');

  const bodyData = new FormData();
  Object.entries(request).forEach(([keyName, value]) => {
    bodyData.append(keyName, value);
  });

  const response = await axios.post(url, request, {
    headers: {
      // Authorization: `Bearer ${accessToken}`,
      'Content-Type': "'multipart/form-data'",
    },
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchGetBoardDeetail : 글 상세 조회
 * @param boardId
 * @returns
 */
export const fetchGetBoardDetail = async (boardId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${boardId}`;

  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
  });

  return response.data.result;
};

/**
 * fetchGetBoardList : 메인 화면 게시글 목록, 현재 분류 조건에 따른 결과가 백API로 미구현임
 * @param variables : 카테고리 목록, content : 정렬기준
 * @returns
 */
export const fetchGetBoardList = async (request: IGetBoardListRequest) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards`;
  const params: IGetBoardListParams = {
    page: 0,
    categories: [CATEGORY_LIST.en[0]],
    sortBy: SORT_LIST_EN[0],
  };

  params.page = request.pageParam;
  if (request.variables) {
    params.categories = request.variables.map(idx => CATEGORY_LIST.en[idx]);
  }
  if (request.content) {
    params.sortBy = request.content;
  }
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    params,
    paramsSerializer: param => {
      return qs.stringify(param);
    },
  });

  return response.data.result;
};

/**
 * fetchGetSearchBoard : 태그 검색 결과에 따라 나타나는 게시글 목록, 태그 검색은 string이므로 request에 content라는 속성으로 받음
 * @param request
 * @returns
 */
export const fetchGetSearchBoard = async (request: IGetPageableListRequest) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;
  const url = `${API_URL}/api/boards/search`;
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    params: { page: request.pageParam, content: request.content },
  });

  return response.data.result;
};

/**
 * fetchGetWrittenBoard: 작성자가 작성한 글 목록을 요청하는 API, useInfinityQueryProduct에서 string을 보내는 변수명은 content로 고정되어 있어 함수 내에서 content를 사용한다ㅏ.
 * 여기서 content는 memberId가 들어 있다.
 * @param request
 * @returns
 */
export const fetchGetWrittenBoard = async (
  request: IGetPageableListRequest,
) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/members/${request.content}`;
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    params: { page: request.pageParam },
  });

  return response.data.result.content;
};

/**
 * fetchGetYummyBoard : yummy 누른 목록을 요청하는 API, useInfinityQueryProduct에서 string을 보내는 변수명은 content로 고정되어 있어 함수 내에서 content를 사용한다ㅏ.
 * 여기서 content는 memberId가 들어 있다.
 * @param request
 * @returns
 */
export const fetchGetYummyBoard = async (request: IGetPageableListRequest) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;
  const url = `${API_URL}/api/boards/members/${request.content}/yummys`;
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    params: { page: request.pageParam },
  });

  return response.data.result.content;
};

/**
 * fetchGetTodayYummys : 오늘의 얌 리스트나, 오늘의 얌을 요청하는 axios
 * @param isTop : 오늘의 얌인지 아닌지 결정하는 boolean
 * @returns
 */
export const fetchGetTodayYummys = async (isTop: boolean) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/yummys${isTop ? '/top' : ''}`;
  const response = await axios.get(url, {
    withCredentials: true,
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
  });
  if (isTop) {
    return response.data.result[0];
  }
  return response.data.result;
};

/**
 * fetchPostAddYummy: yummy 등록 post 요청
 * @param boardId
 * @returns
 */
export const fetchPostAddYummy = async (boardId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${boardId}/yummys`;

  const response = await axios.post(url, null, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchDeleteYummy : yummy 취소 delete 요청
 * @param boardId
 * @returns
 */
export const fetchDeleteYummy = async (boardId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${boardId}/yummys`;

  const response = await axios.delete(url, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};

/**
 * fetchPatchFilm : 글 수정 API 요청
 * @param input : 프론트에서 쓰는 데이터 형식으로 request를 보내고, 이 함수 내에서 백에 맞게 수정함
 * @returns
 */
export const fetchPatchFilm = async (input: IUpdateBoardRequest) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${input.boardId}`;
  const request: IUpdateBoardRequestBody = { ...input };

  request.category = CATEGORY_LIST.en[request.category as number];
  request.mealTime = TIME_LIST[request.mealTime as number].en;
  // 원래 리무브대그에는 있는데  지금 태그스에 없는 애들
  request.removedTags = request.removedTags.filter(
    element => !request.tags.includes(element),
  );

  const body = new FormData();
  Object.entries(request).forEach(([key, value]) => {
    body.append(key, value);
  });

  const response = await axios.post(url, body, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};

export const fetchDeleteFilm = async (boardId: string) => {
  // const accessToken = localStorage.getItem('token');
  // if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${boardId}`;

  const response = await axios.delete(url, {
    // headers: {
    //   Authorization: `Bearer ${accessToken}`,
    // },
    withCredentials: true,
  });
  return response.data;
};
