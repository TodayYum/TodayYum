import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faPencil } from '@fortawesome/free-solid-svg-icons';
import { ICommentContainer } from '../types/components/CommentContainer.types';

function CommentContainer(props: ICommentContainer) {
  const handleEditComment = () => {
    if (!props.setEdit) return;
    props.setEdit();
  };

  return (
    <div
      className={`flex justify-between p-3 pl-0 gap-2 bg-white rounded w-[calc(100vw-30px)] ${props.isPreview && 'shadow-lg fixed bottom-20 mx-[15px] '}`}
    >
      <Link
        to="/"
        className="flex-[0_0_110px] leading-5 flex flex-col justify-center items-center gap-y-2"
      >
        {!props.isPreview && (
          <img
            src="/t3.jpg"
            alt="프로필 사진"
            className="rounded-full h-10 w-10"
          />
        )}
        <span className="font-bold text-sm">{props.nickname}</span>
      </Link>
      <div className="flex-[1_1_60%]">
        <div className="flex justify-between items-center">
          {!props.isPreview && <p className="text-xs text-gray ">몇초 전</p>}
          {!props.isPreview && DUMMY_ISMINE && (
            <div>
              <FontAwesomeIcon
                icon={faPencil}
                className="text-gray-dark mr-2"
                onClick={handleEditComment}
              />
              <FontAwesomeIcon
                icon={faClose}
                className="text-gray-dark text-xl"
              />
            </div>
          )}
        </div>
        <span className={`text-sm  ${props.isPreview && 'line-clamp-1'}`}>
          {props.comment}
        </span>
      </div>
    </div>
  );
}

const DUMMY_ISMINE = true;
export default CommentContainer;
