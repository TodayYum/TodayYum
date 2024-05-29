import { Navigate, Outlet } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
// import { Navigate, Outlet, useNavigate } from 'react-router-dom';
// import { useMutation, useQuery } from '@tanstack/react-query';
import {
  fetchGetUserInfo,
  //   fetchPostRefreshToken,
} from '../services/userService';

function CheckAuthRoute() {
  //   const navigate = useNavigate();
  const { data, isSuccess } = useQuery({
    queryKey: ['userProfile', localStorage.getItem('memberId')],
    queryFn: () => fetchGetUserInfo(localStorage.getItem('memberId') ?? ''),
    staleTime: 0,
  });
  //   const { mutate: useRefreshToken, isSuccess: refreshSuccess } = useMutation({
  //     mutationFn: () => fetchPostRefreshToken(),
  //     onError: () => {
  //       alert('로그인이 필요합니다.');
  //       navigate('/login');
  //     },
  //   });

  if (isSuccess) {
    switch (data.message) {
      case '요청이 완료되었습니다.':
        return <Outlet />;
      case 'Request failed with status code 404':
        alert('아이디가 잘못되었습니다.');
        break;
      case 'Request failed with status code 403':
        // 현재 accessToken 만료 기간이 지났을 경우, refresh되지가 않음
        alert('토큰이 만료되었습니다.');
        // useRefreshToken();
        // if (refreshSuccess) {
        //   return <Outlet />;
        // }
        break;
      default:
        alert('알 수 없는 에러입니다.');
    }
    return <Navigate to="/login" />;
  }

  return <Outlet />;
}

export default CheckAuthRoute;
