import axios from 'axios';
import { IGetPageableListRequest } from '../types/services/boardService';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

export const fetchGetCommentList = async (request: IGetPageableListRequest) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${request.content}/comments`;

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

export const fetchPostAddComment = async (request: {
  content: string;
  boardId: string;
}) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/boards/${request.boardId}/comments`;

  const body = new FormData();
  body.append('content', request.content);

  const response = await axios.post(
    url,
    { content: request.content },
    {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  return response.data.result;
};

export const fetchDeleteComment = async (commentId: number) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/boards/comments/${commentId}`;

  const response = await axios.delete(url, {
    withCredentials: true,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  return response.data;
};

export const fetchPatchEditComment = async (request: {
  content: string;
  commentId: number;
}) => {
  const accessToken = localStorage.getItem('token');
  if (!accessToken) return false;

  const url = `${API_URL}/api/boards/comments/${request.commentId}`;

  const response = await axios.patch(
    url,
    { content: request.content },
    {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  return response.data;
};
