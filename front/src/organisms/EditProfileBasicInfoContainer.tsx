import { useState, useRef, ChangeEvent } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import {
  userProfileAtom,
  useSetCommentAtom,
  useSetNicknameAtom,
  useSetProfileImgAtom,
} from '../jotai/userProfile';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import { IEditProfileBasicInfoContainer } from '../types/organisms/EditProfileBasicInfoContainer.types';

function EditProfileBasicInfoContainer(props: IEditProfileBasicInfoContainer) {
  const userProfile = userProfileAtom();
  const setNicknameAtom = useSetNicknameAtom();
  const setCommentAtom = useSetCommentAtom();
  const setProfileImgAtom = useSetProfileImgAtom();
  const [nickname, setNickname] = useState(userProfile.nickname);
  const [comment, setComment] = useState(userProfile.comment);
  const imgInputRef = useRef<HTMLInputElement>(null);

  const handleSetNickNameValue = (input: string) => {
    setNickname(input);
  };
  const handleSetCommentValue = (input: string) => {
    setComment(input);
  };

  const handleSetNicknameButton = () => {
    // API 요청 후
    setNicknameAtom(nickname);
  };
  const handleCommentButton = () => {
    // API 요청 후
    setCommentAtom(comment);
  };
  const handleClickUploadButton = () => {
    imgInputRef.current?.click();
  };
  const handleUploadImg = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files?.length) return;
    const target = e.target.files[0];
    // API로 등록 후 받은 S3 주소를 profile에 넣을 것
    setProfileImgAtom({
      profileFile: target,
      profile: window.URL.createObjectURL(target),
    });
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
            src={userProfile.profile ?? '/t1.jpg'}
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
        customClass="mb-[30px]"
      />
      <RectangleButton
        text="비밀번호 재설정"
        onClick={props.goNextLevel}
        customClass="w-full"
      />
    </div>
  );
}

export default EditProfileBasicInfoContainer;
