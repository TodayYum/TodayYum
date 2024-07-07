import { useState, useRef, ChangeEvent } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import {
  userProfileAtom,
  useSetCommentAtom,
  useSetNicknameAtom,
  useSetProfileImgAtom,
} from '../jotai/userProfile';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { IEditProfileBasicInfoContainer } from '../types/organisms/EditProfileBasicInfoContainer.types';
import {
  fetchDeleteUser,
  fetchPatchEditIntroduction,
  fetchPatchEditNickname,
  fetchPostEditProfileImg,
} from '../services/userService';

function EditProfileBasicInfoContainer(props: IEditProfileBasicInfoContainer) {
  const userProfile = userProfileAtom();
  const nagivate = useNavigate();
  const setNicknameAtom = useSetNicknameAtom();
  const setCommentAtom = useSetCommentAtom();
  const setProfileImgAtom = useSetProfileImgAtom();
  const [nickname, setNickname] = useState(userProfile.nickname);
  const [comment, setComment] = useState(userProfile.introduction);
  const imgInputRef = useRef<HTMLInputElement>(null);
  const { mutate: patchNickname } = useMutation({
    mutationFn: (request: string) => fetchPatchEditNickname(request),
    onSuccess: response => {
      if (response) {
        setNicknameAtom(nickname);
      }
      // !NOTICE 실패할 경우?
    },
  });
  const { mutate: patchIntroduction } = useMutation({
    mutationFn: (request: string) => fetchPatchEditIntroduction(request),
    onSuccess: response => {
      if (response) {
        setCommentAtom(comment);
      }
      // !NOTICE 실패할 경우?
    },
  });
  const { mutate: postEditProfile } = useMutation({
    mutationFn: (request: File) => fetchPostEditProfileImg(request),
    onSuccess: (response, input) => {
      if (response) {
        setProfileImgAtom({
          profileFile: input,
          profile: window.URL.createObjectURL(input),
        });
      }
      // !NOTICE 실패할 경우?
    },
  });
  const { mutate: signOut } = useMutation({
    mutationFn: () => fetchDeleteUser(),
    onSuccess: () => {
      nagivate('/login');
    },
  });

  // props 함수
  const handleSetNickNameValue = (input: string) => {
    setNickname(input);
  };
  const handleSetCommentValue = (input: string) => {
    setComment(input);
  };

  // API 요청
  const handleSetNicknameButton = () => {
    patchNickname(nickname);
  };
  const handleCommentButton = () => {
    patchIntroduction(comment);
  };
  const handleClickUploadButton = () => {
    imgInputRef.current?.click();
  };
  const handleUploadImg = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files?.length) return;
    const target = e.target.files[0];
    // API로 등록 후 받은 S3 주소를 profile에 넣을 것
    postEditProfile(target);
  };

  return (
    <div>
      <div className="flex gap-4">
        <div className="h-[70px] w-[70px] relative flex-[0_0_70px] mt-3">
          <input
            type="file"
            onChange={handleUploadImg}
            className="hidden"
            ref={imgInputRef}
          />
          <img
            src={userProfile.profile ?? '/default_profile.png'}
            alt="프로필 사진 수정 버튼"
            className="rounded-full opacity-60 h-[70px] w-[70px] object-cover"
          />
          <FontAwesomeIcon
            icon={faPlus}
            className="absolute absolute-center text-5xl text-gray-dark/80"
            onClick={handleClickUploadButton}
          />
        </div>
        <div className="w-full">
          <p className="base-bold mb-2">닉네임</p>
          <InputText
            hasSupport
            placeholder="닉네임"
            type="text"
            hasButton
            value={nickname}
            setValue={handleSetNickNameValue}
            onClickUpload={handleSetNicknameButton}
            isSuccess={-1}
            failText="중복된 닉네임이 있습니다."
            successText="닉네임이 변경되었습니다."
          />
        </div>
      </div>
      <p className="base-bold">한줄 소개</p>
      <InputText
        hasSupport={false}
        placeholder="한줄 소개"
        type="text"
        hasButton
        value={comment}
        setValue={handleSetCommentValue}
        onClickUpload={handleCommentButton}
        customClass="mb-[40px]"
      />
      <RectangleButton
        text="비밀번호 재설정"
        onClick={props.goNextLevel}
        customClass="w-full mb-[15px]"
      />
      <p
        role="presentation"
        className="text-right text-gray-dark"
        onKeyUp={() => {}}
        onClick={() => {
          signOut();
        }}
      >
        탈퇴
      </p>
    </div>
  );
}

export default EditProfileBasicInfoContainer;
