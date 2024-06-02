// import { Navigate, Outlet } from 'react-router-dom';
// import { useQuery } from '@tanstack/react-query';
// import { Outlet } from 'react-router-dom';
import { Outlet, useNavigate } from 'react-router-dom';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Swal from 'sweetalert2';
import { useEffect, useRef } from 'react';
import {
  fetchGetUserInfo,
  fetchPostRefreshToken,
} from '../services/userService';
import setSwalText from '../util/swalUtil';
import { swalNavigateLogin } from '../constant/swalConstant';
import useSignInDataAtom from '../jotai/signInData';

// 토큰이 있는가?
// - 토큰 있으면 그거로 프로필 요청
// - 403 뜨면 리프레시 요청
// - 토큰 없으면 리프레시 요청
function CheckAuthRoute() {
  const [signInData, setSignInData] = useSignInDataAtom();
  const refdata = useRef(0);
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { data, isSuccess } = useQuery({
    queryKey: ['userProfile', signInData.memberId],
    queryFn: () => fetchGetUserInfo(signInData.memberId),
    staleTime: 0,
  });
  // const abc = false;
  refdata.current += 1;
  console.log(
    '체크라우트 초기값 확인',
    signInData,
    refdata.current,
    data,
    signInData.token,
  );
  const { mutate: useRefreshToken } = useMutation({
    mutationFn: () => fetchPostRefreshToken(signInData.memberId),
    onSuccess: response => {
      console.log('리프레시 결과 확인', response);
      setSignInData({
        memberId: '',
        nickname: '',
        token: response.data.token,
      });
      // refetch();
      queryClient.removeQueries({
        queryKey: ['userProfile', signInData.memberId],
        exact: true,
      });
    },
    onError: err => {
      console.log('설마이거임', err);
      navigate('/login');
    },
  });

  useEffect(() => {
    let isOk = false;
    if (isSuccess) {
      const swalOption = swalNavigateLogin;
      console.log('12312313', data);
      switch (data.message) {
        case '요청이 완료되었습니다.':
          isOk = true;
          break;
        case 'Request failed with status code 404':
          setSwalText(swalOption, '잘못된 입력입니다.');
          break;
        case 'Request failed with status code 403':
          useRefreshToken();
          isOk = true;
          break;
        default:
          setSwalText(swalOption, '먼저 로그인해주세요.');
      }
      if (!isOk) {
        Swal.fire(swalOption).then(res => {
          console.log(res);
          if (res.isConfirmed) {
            navigate('/login');
          }
        });
      }
    }
  }, [isSuccess]);

  // if (isSuccess && refdata.current < 10) {
  //   const swalOption = swalNavigateLogin;
  //   switch (data.message) {
  //     case '요청이 완료되었습니다.':
  //       return <Outlet />;
  //       break;
  //     case 'Request failed with status code 404':
  //       setSwalText(swalOption, '잘못된 입력입니다.');
  //       break;
  //     case 'Request failed with status code 403':
  //       // 현재 accessToken 만료 기간이 지났을 경우, refresh되지가 않음
  //       // setSwalText(
  //       //   swalOption,
  //       //   '로그인이 만료되었습니다. \n 다시 로그인해주세요.',
  //       // );
  //       // if (!abc) {
  //       //   if (!refdata.current) {
  //       useRefreshToken();
  //       // if (refreshSuccess) {
  //       //   return <Outlet />;
  //       // }
  //       //   }
  //       // }
  //       break;
  //     default:
  //       setSwalText(swalOption, '먼저 로그인해주세요.');
  //   }
  //   Swal.fire(swalOption).then(res => {
  //     console.log(res);
  //     if (res.isConfirmed) {
  //       navigate('/login');
  //     }
  //   });
  //   // return <Navigate to="/login" />;
  // }

  return <Outlet />;
}

export default CheckAuthRoute;
