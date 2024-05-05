import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeftLong } from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
import CommentContainer from '../atoms/CommentContainer';
import { ICommentList } from '../types/organisms/CommentList.types';
import EditComment from '../atoms/EditComment';
import useIntersect from '../util/useIntersect';

// 연동 시 파일로 뺼 것, edit와 commentcontainer에 extand할 것
interface IComment {
  nickname: string;
  comment: string;
}

function CommentList(props: ICommentList) {
  const [idxOfEditComment, setIdxOfEditComment] = useState(-1);
  const [commentList, setCommentList] = useState<IComment[]>(DUMMY_COMMENTLIST);
  const [, setRef] = useIntersect(async (entry, observer) => {
    const hasNext = true;
    const newList = commentList.concat(JSON.parse(JSON.stringify(commentList)));
    setCommentList(newList);
    observer.unobserve(entry.target);
    return hasNext;
  }, {});
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
        {commentList.map((element, idx) =>
          idxOfEditComment === idx ? (
            <EditComment
              commentId={idx}
              exitEdit={() => setIdxOfEditComment(-1)}
              comment={element.comment}
            />
          ) : (
            <CommentContainer
              comment={element.comment}
              nickname={element.nickname}
              setEdit={() => setIdxOfEditComment(idx)}
            />
          ),
        )}
        <div ref={setRef} className="opacity-0">
          now loading
        </div>
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
