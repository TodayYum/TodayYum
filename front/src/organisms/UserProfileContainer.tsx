import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faHeart as faHeartFill,
  faPencil,
} from '@fortawesome/free-solid-svg-icons';
import { faHeart } from '@fortawesome/free-regular-svg-icons';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import EditProfileModal from './EditProfileModal';
import { userProfileAtom } from '../jotai/userProfile';
import { fetchDeleteFollow, fetchPostAddFollow } from '../services/userService';
import { IUserPofileContainer } from '../types/organisms/UserProfileContainer.types';
import useSignInDataAtom from '../jotai/signInData';

function UserProfileContainer(props: IUserPofileContainer) {
  const [isOnEdit, setIsOnEdit] = useState(false);
  const [signInData] = useSignInDataAtom();
  const userProfile = userProfileAtom();
  const navigate = useNavigate();
  const { mutate: handleFollow } = useMutation({
    mutationFn: (follow: boolean) =>
      follow
        ? fetchDeleteFollow(userProfile.memberId)
        : fetchPostAddFollow(userProfile.memberId),
    onSuccess: () => {
      props.refetch();
    },
  });

  const handleFollowButton = () => {
    handleFollow(userProfile.following);
  };

  const handleGoFollowings = () => {
    navigate('/user-list', {
      state: {
        title: '팔로잉 목록',
        type: 'following',
        memberId: userProfile.memberId,
      },
    });
  };
  const handleGoFollowers = () => {
    navigate('/user-list', {
      state: {
        title: '팔로워 목록',
        type: 'follower',
        memberId: userProfile.memberId,
      },
    });
  };

  return (
    <div className="bg-white rounded-2xl m-[15px] p-[15px]">
      <div className="flex flex-row items-center justify-between mb-[15px] gap-3">
        <img
          src={userProfile.profile ?? '/default_profile.png'}
          alt="프로필 사진"
          className="h-[45px] w-[45px] object-cover rounded-full"
        />
        <div className="font-ggTitle text-base w-full">
          <p className="font-bold mb-2">{userProfile.nickname}</p>
          <p className="h-6">{userProfile.introduction}</p>
        </div>
        {userProfile.memberId === signInData.memberId ? (
          <FontAwesomeIcon
            icon={faPencil}
            className="text-gray-dark mx-1 text-xl"
            onClick={() => setIsOnEdit(true)}
          />
        ) : (
          <FontAwesomeIcon
            icon={userProfile.following ? faHeartFill : faHeart}
            className="mr-1 text-primary text-2xl"
            onClick={handleFollowButton}
          />
        )}
      </div>
      <hr className="border-gray-dark border-[0.5px] my-[15px]" />
      <div className="flex justify-around text-center">
        <div
          onClick={handleGoFollowers}
          tabIndex={0}
          onKeyUp={() => {}}
          role="button"
        >
          <p className="base-bold">팔로워</p>
          <p className="text-xl">
            {userProfile.followerCount.toLocaleString()}
          </p>
        </div>
        <div
          onClick={handleGoFollowings}
          tabIndex={-1}
          onKeyUp={() => {}}
          role="button"
        >
          <p className="base-bold">팔로잉</p>
          <p className="text-xl">
            {userProfile.followingCount.toLocaleString()}
          </p>
        </div>
      </div>
      {isOnEdit && (
        <EditProfileModal
          onClose={() => {
            setIsOnEdit(false);
          }}
        />
      )}
    </div>
  );
}

export default UserProfileContainer;
