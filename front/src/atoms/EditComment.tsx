import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { ChangeEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import { IEditComment } from '../types/components/EditComment.types';

function EditComment(props: IEditComment) {
  const [comment, setComment] = useState(props.comment);

  const handleCommentChange = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  const handleSubmitChange = () => {
    props.editComment({ content: comment, commentId: props.commentId });
    props.exitEdit();
  };

  return (
    <div className="flex justify-between p-3 pl-0 gap-2 bg-white rounded w-[calc(100vw-30px)]">
      <Link
        to="/"
        className="flex-[0_0_110px] leading-5 flex flex-col justify-center items-center gap-y-2 pointer-events-none"
      >
        <img
          src={props.profile ?? '/default_profile.png'}
          alt="프로필 사진"
          className="rounded-full h-10 w-10"
        />
        <span className="font-bold text-sm">{props.nickname}</span>
      </Link>
      <input
        type="text"
        placeholder="댓글을 입력해주세요!"
        className="w-full self-start h-full  border-none focus:outline-none placeholder:font-ggTitle placeholder:text-sm font-ggTitle"
        value={comment}
        onChange={handleCommentChange}
      />
      <div className="flex flex-col justify-between">
        <FontAwesomeIcon
          icon={faClose}
          className="text-xl text-gray-dark"
          onClick={props.exitEdit}
        />
        <FontAwesomeIcon
          icon={faPaperPlane}
          className="text-secondary my-auto"
          onClick={handleSubmitChange}
        />
      </div>
    </div>
  );
}

export default EditComment;
