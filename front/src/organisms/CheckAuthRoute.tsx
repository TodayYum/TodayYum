// import { Navigate, Outlet } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
// import { Outlet } from 'react-router-dom';
import { Outlet, useNavigate } from 'react-router-dom';
// import { useMutation, useQuery } from '@tanstack/react-query';
import Swal from 'sweetalert2';
import {
  fetchGetUserInfo,
  //   fetchPostRefreshToken,
} from '../services/userService';
import setSwalText from '../util/swalUtil';
import { swalNavigateLogin } from '../constant/swalConstant';

function CheckAuthRoute() {
  const navigate = useNavigate();
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
    const swalOption = swalNavigateLogin;
    switch (data.message) {
      case '요청이 완료되었습니다.':
        return <Outlet />;
        break;
      case 'Request failed with status code 404':
        setSwalText(swalOption, '잘못된 입력입니다.');
        break;
      case 'Request failed with status code 403':
        // 현재 accessToken 만료 기간이 지났을 경우, refresh되지가 않음
        setSwalText(
          swalOption,
          '로그인이 만료되었습니다. \n 다시 로그인해주세요.',
        );
        // useRefreshToken();
        // if (refreshSuccess) {
        //   return <Outlet />;
        // }
        break;
      default:
        setSwalText(swalOption, '먼저 로그인해주세요.');
    }
    Swal.fire(swalOption).then(res => {
      console.log(res);
      if (res.isConfirmed) {
        navigate('/login');
      }
    });
    // return <Navigate to="/login" />;
  }

  return <Outlet />;
}

export default CheckAuthRoute;
