import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useMutation } from '@tanstack/react-query';
import { ICommentInput } from '../types/components/CommentInput.types';
import { fetchPostAddComment } from '../services/commentService';

function CommentInput(props: ICommentInput) {
  const { mutate: addComent } = useMutation({
    mutationFn: (request: { content: string; boardId: string }) =>
      fetchPostAddComment(request),
    onSuccess: res => {
      console.log(res);
      props.refetch();
    },
  });
  const handleSubmit = () => {
    // 댓글 제출 API
    addComent({ content: props.value, boardId: props.boardId });
  };
  return (
    <div className="flex justify-between p-3 gap-2 bg-white rounded w-[calc(100vw-30px)] shadow-lg fixed bottom-20 mx-[15px]">
      <span className="flex-[0_0_110px] font-bold text-sm">
        {localStorage.getItem('nickname')}
      </span>
      <input
        type="text"
        placeholder="댓글을 입력해주세요!"
        className="w-full  border-none focus:outline-none placeholder:font-ggTitle placeholder:text-sm"
        value={props.value}
        onChange={props.onChange}
      />
      <FontAwesomeIcon
        icon={faPaperPlane}
        className="text-secondary my-auto"
        onClick={handleSubmit}
      />
    </div>
  );
}

export default CommentInput;
