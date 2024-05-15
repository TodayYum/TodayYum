import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faHeart, faPencil } from '@fortawesome/free-solid-svg-icons';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import Header from '../atoms/Header';
import SelectScore from '../atoms/SelectScore';
import CommentContainer from '../atoms/CommentContainer';
import CommentList from '../organisms/CommenetList';
import CommentInput from '../atoms/CommentInput';
import { fetchGetBoardDetail } from '../services/boardService';
import { CATEGORY_LIST, CATEGORY_MAP } from '../constant/searchConstant';
import { ISOtoLocal } from '../util/dateUtil';

function FilmDetailPage() {
  const fixedContentsRef = useRef<HTMLDivElement>(null);
  const { boardId } = useParams();
  const [height, setHeight] = useState(490);
  const [isMainContents, setIsMainContents] = useState(true);
  const [commentInputValue, setCommentInputValue] = useState('');
  const navigate = useNavigate();
  const { data, isSuccess } = useQuery({
    queryKey: ['boardDetail', boardId],
    queryFn: () => fetchGetBoardDetail(boardId ?? '0'),
  });
  console.log(data);
  const resizeObserver = new ResizeObserver(entries => {
    // 하나만 observe할 것이므로 forEach를 사용하지 않는다.
    if (entries[0].target === fixedContentsRef.current) {
      setHeight(entries[0].contentRect.height);
    }
  });
  useEffect(() => {
    if (fixedContentsRef.current) {
      resizeObserver.observe(fixedContentsRef.current);
    }

    return () => {
      resizeObserver.disconnect();
    };
  }, [fixedContentsRef.current]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCommentInputValue(e.target.value);
  };
  if (!isSuccess) return <div>로오딩</div>;

  return (
    <div>
      <Header
        title={CATEGORY_LIST.kr[CATEGORY_MAP[data.category]]}
        ateAt={ISOtoLocal(data.ateAt)}
      />
      {/* 상단부 */}
      <div className="fixed overflow-hidden top-[58px]" ref={fixedContentsRef}>
        <DetailPageCarousel imgs={data.images} />
        {/* 프로필 */}
        <div className="bg-white flex justify-between p-[15px]">
          <div className="flex items-center gap-2">
            <img
              src={data.profile ?? '/t3.jpg'}
              alt="프로필사진"
              className="rounded-full h-7 w-7"
            />
            <span className="base-bold my-auto">{data.nickname}</span>
          </div>
          <div>
            <FontAwesomeIcon icon={faHeart} className="text-error mr-2" />
            <span className="text-base">{data.yummyCount}</span>
            {DUMMY_ISMINE && (
              <FontAwesomeIcon
                icon={faPencil}
                className="ml-2 text-gray-dark"
                onClick={() => navigate('/edit-film')}
              />
            )}
            {DUMMY_ISMINE && (
              <FontAwesomeIcon
                icon={faClose}
                className="ml-2 text-gray-dark text-xl"
                onClick={() => navigate('/edit-film')}
              />
            )}
          </div>
        </div>
      </div>
      {/* 하단부 */}
      {isMainContents ? (
        <div className="bg-white p-[15px]" style={{ marginTop: height }}>
          <p className="text-base my-[15px]">{data.content}</p>
          {data.tags.map((element: string) => (
            <span className="text-base mr-2"># {element}</span>
          ))}
          {/* <p className="text-base my-5">서울특별시 강남구 테헤란로 212</p> */}
          <SelectScore
            score={[data.tasteScore, data.priceScore, data.moodScore]}
          />
        </div>
      ) : (
        <CommentList
          onClick={() => setIsMainContents(true)}
          marginTop={height}
          boardId={Number(boardId ?? '0')}
        />
      )}
      <div className="bg-black h-36 invisible ">asdf</div>
      {isMainContents ? (
        <div
          onClick={() => setIsMainContents(false)}
          onKeyUp={() => {}}
          tabIndex={0}
          role="button"
          aria-label="댓글 프리뷰"
        >
          <CommentContainer
            nickname={
              data.commentWriter ??
              `${localStorage.getItem('nickname') ?? ''} 님`
            }
            comment={data.comment ?? '첫 댓글을 입력해주세요!'}
            isPreview
          />
        </div>
      ) : (
        <CommentInput value={commentInputValue} onChange={handleChange} />
      )}
    </div>
  );
}

const DUMMY_ISMINE = true;
export default FilmDetailPage;
