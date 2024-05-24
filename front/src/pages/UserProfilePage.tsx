/**
 * UserProfilePage : 유저데이터 페이지 조회(마이페이지)
 *
 */

import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronRight,
  faRightFromBracket,
} from '@fortawesome/free-solid-svg-icons';
import { useMutation, useQuery } from '@tanstack/react-query';
import Header from '../atoms/Header';
import UserProfileContainer from '../organisms/UserProfileContainer';
import PolaroidFilm from '../organisms/PolaroidFilm';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
import {
  fetchGetWrittenBoard,
  fetchGetYummyBoard,
} from '../services/boardService';
import { fetchGetUserInfo, fetchPostSignOut } from '../services/userService';
import { useSetProfileAtom } from '../jotai/userProfile';

function UserProfilePage() {
  const locationState = useLocation().state;
  const setProfile = useSetProfileAtom();
  const navigate = useNavigate();
  const {
    data: userProfile,
    isSuccess: isProfileSuccess,
    refetch,
  } = useQuery({
    queryKey: ['userProfile', locationState.memberId],
    queryFn: () => fetchGetUserInfo(locationState.memberId),
    staleTime: 500000,
  });

  if (isProfileSuccess) {
    setProfile(userProfile);
  }
  const { data: yummyList, isSuccess: isYummySuccess } = useQuery({
    queryKey: ['yummyBoardList', locationState.memberId],
    queryFn: () =>
      fetchGetYummyBoard({ pageParam: 0, content: locationState.memberId }),
    staleTime: 500000,
  });
  const { data: writtenList, isSuccess: isWrittenSuccess } = useQuery({
    queryKey: ['writtenBoardList', locationState.memberId],
    queryFn: () =>
      fetchGetWrittenBoard({ pageParam: 0, content: locationState.memberId }),
    staleTime: 500000,
  });

  const { mutate: signOut } = useMutation({
    mutationFn: () => fetchPostSignOut(),
    onSuccess: () => {
      navigate('/login');
    },
  });

  const handleGoMyFilms = () => {
    navigate('/write-list', {
      state: {
        title: '작성한 글',
        memberId: '80ad8672-3501-431f-8a9a-0d0f1c02f2c3',
        type: 'written',
      },
    });
  };

  const handleGoYummyList = () => {
    navigate('/write-list', {
      state: {
        title: 'Yummy 목록',
        memberId: '80ad8672-3501-431f-8a9a-0d0f1c02f2c3',
        type: 'yummy',
      },
    });
  };

  return (
    <div className="relative">
      <Header
        title={`${localStorage.getItem('memberId') === locationState?.memberId ? '마이페이지' : '프로필페이지'}`}
      />
      {localStorage.getItem('memberId') === locationState?.memberId && (
        <FontAwesomeIcon
          icon={faRightFromBracket}
          onClick={() => signOut()}
          className="absolute text-gray-dark text-xl top-5 right-5"
        />
      )}
      <UserProfileContainer refetch={refetch} />
      <div
        className="flex justify-between mx-[15px]"
        onClick={handleGoMyFilms}
        tabIndex={0}
        onKeyUp={() => {}}
        role="button"
      >
        <p className="base-bold ml-2">작성한 글</p>
        <FontAwesomeIcon icon={faChevronRight} />
      </div>
      <div className="flex w-[calc(100%-30px)] overflow-auto gap-4 mx-[15px]">
        {isWrittenSuccess &&
          ((writtenList as IPolaroidFilm[]) ?? []).map(element => (
            <PolaroidFilm
              tag={element.tag}
              thumbnail={element.thumbnail}
              id={element.id}
              totalScore={element.totalScore}
              yummyCount={element.yummyCount}
              category={element.category}
            />
          ))}
      </div>
      <div
        className="flex justify-between mx-[15px] mt-[15px]"
        onClick={handleGoYummyList}
        tabIndex={0}
        onKeyUp={() => {}}
        role="button"
      >
        <p className="base-bold ml-2">Yummy 목록</p>
        <FontAwesomeIcon icon={faChevronRight} />
      </div>
      <div className="flex w-[calc(100%-30px)] overflow-auto gap-4 mx-[15px]">
        {isYummySuccess &&
          (yummyList as IPolaroidFilm[]).map(element => (
            <PolaroidFilm
              tag={element.tag}
              thumbnail={element.thumbnail}
              id={element.id}
              totalScore={element.totalScore}
              yummyCount={element.yummyCount}
              category={element.category}
            />
          ))}
      </div>
    </div>
  );
}

export default UserProfilePage;
