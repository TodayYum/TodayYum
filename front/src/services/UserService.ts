import axios from 'axios';

const API_URL = process.env.REACT_APP_LOCAL_URL;
// const url = process.env.REACT_APP_SERVER_URL;

export const fetchLogin = () => {};

export const fetchFindPassword = () => {};

// 이메일 중복 검사 요청 API
export const fetchCheckEmailDuplicate = async (email: string) => {
  const url = `${API_URL}/api/members/emails/validations`;
  console.log('fetchRace 중 gameCode', url);
  try {
    const response = await axios.get(url, {
      params: { email },
    });
    console.log('종족 목록', response.data);
    return response.data;
  } catch (err) {
    console.log(err, '종족 요청 실패');
    return 3;
  }
};
