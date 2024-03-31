import { Link } from 'react-router-dom';
import { ICommentContainer } from '../types/components/CommentContainer.types';

function CommentContainer(props: ICommentContainer) {
  return (
    <div
      className={`flex justify-between p-3 gap-2 bg-white rounded w-[calc(100vw-30px)] ${props.isPreview && 'shadow-lg fixed bottom-20 mx-[15px] '}`}
    >
      <Link to="/" className="flex-[0_0_110px] leading-5">
        <span className="font-bold text-sm">{props.nickname}</span>
      </Link>
      <span
        className={`text-sm flex-[1_1_60%] ${props.isPreview && 'line-clamp-1'}`}
      >
        {props.comment}
      </span>
    </div>
  );
}

export default CommentContainer;
