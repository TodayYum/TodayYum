import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeftLong } from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import CommentContainer from '../atoms/CommentContainer';
import { ICommentList } from '../types/organisms/CommentList.types';
import EditComment from '../atoms/EditComment';
import {
  fetchDeleteComment,
  fetchPatchEditComment,
} from '../services/commentService';

function CommentList(props: ICommentList) {
  const [idxOfEditComment, setIdxOfEditComment] = useState(-1);
  const { mutate: deleteComment } = useMutation({
    mutationFn: (commentId: number) => fetchDeleteComment(commentId),
    onSuccess: () => {
      props.refetch();
    },
  });

  const { mutate: editComment } = useMutation({
    mutationFn: (request: { commentId: number; content: string }) =>
      fetchPatchEditComment(request),
    onSuccess: () => {
      props.refetch();
    },
  });
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
        {props.commentList.map((comment, idx) =>
          idxOfEditComment === idx ? (
            <EditComment
              nickname={comment.nickname}
              profile={comment.profile}
              commentId={comment.id}
              exitEdit={() => setIdxOfEditComment(-1)}
              comment={comment.content}
              editComment={editComment}
              key={comment.id}
            />
          ) : (
            <CommentContainer
              commentId={comment.id}
              profile={comment.profile}
              modifiedAt={comment.modifiedAt}
              memberId={comment.memberId}
              comment={comment.content}
              nickname={comment.nickname}
              setEdit={() => setIdxOfEditComment(idx)}
              deleteComment={deleteComment}
              key={comment.id}
            />
          ),
        )}
        <div ref={props.setRef} className="opacity-0">
          now loading
        </div>
      </div>
    </div>
  );
}

export default CommentList;
