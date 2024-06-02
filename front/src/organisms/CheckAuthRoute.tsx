import { Outlet, useNavigate } from 'react-router-dom';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Swal from 'sweetalert2';
import { useEffect } from 'react';
import {
  fetchGetUserInfo,
  fetchPostRefreshToken,
} from '../services/userService';
import setSwalText from '../util/swalUtil';
import { swalNavigateLogin } from '../constant/swalConstant';
import useSignInDataAtom from '../jotai/signInData';

function CheckAuthRoute() {
  const [signInData, setSignInData] = useSignInDataAtom();

  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { data, isSuccess } = useQuery({
    queryKey: ['userProfile', signInData.memberId],
    queryFn: () => fetchGetUserInfo(signInData.memberId),
    staleTime: 0,
  });

  const { mutate: useRefreshToken } = useMutation({
    mutationFn: () => fetchPostRefreshToken(signInData.memberId),
    onSuccess: response => {
      setSignInData({
        memberId: '',
        nickname: '',
        token: response.data.token,
      });
      queryClient.removeQueries({
        queryKey: ['userProfile', signInData.memberId],
        exact: true,
      });
    },
    onError: () => {
      navigate('/login');
    },
  });

  useEffect(() => {
    let isOk = false;
    if (isSuccess) {
      const swalOption = swalNavigateLogin;
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
          if (res.isConfirmed) {
            navigate('/login');
          }
        });
      }
    }
  }, [isSuccess]);

  return <Outlet />;
}

export default CheckAuthRoute;
