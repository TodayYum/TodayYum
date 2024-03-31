import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeftLong } from '@fortawesome/free-solid-svg-icons';
import CommentContainer from '../atoms/CommentContainer';
import { ICommentList } from '../types/organisms/CommentList.types';

function CommentList(props: ICommentList) {
  return (
    <div
      className="bg-white rounded mx-[15px]"
      style={{ marginTop: props.marginTop + 15 }}
    >
      <div className="w-full py-[15px] flex bg-white">
        <FontAwesomeIcon
          icon={faArrowLeftLong}
          onClick={props.onClick}
          className="ml-[15px] mr-5 my-auto"
        />
        <p className="font-bold text-base">Comments</p>
      </div>
      <div>
        {DUMMY_COMMENTLIST.map(element => (
          <CommentContainer
            comment={element.comment}
            nickname={element.nickname}
          />
        ))}
      </div>
    </div>
  );
}

const DUMMY_COMMENTLIST = [
  {
    nickname: '닉네임',
    comment:
      '내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용',
  },
  { nickname: '닉네임', comment: '내용용용용용' },
  { nickname: '닉네임', comment: '내용용용용용' },
  { nickname: '닉네임', comment: '내용용용용용' },
  { nickname: '닉네임', comment: '내용용용용용' },
  { nickname: '닉네임', comment: '내용용용용용' },
];

export default CommentList;
