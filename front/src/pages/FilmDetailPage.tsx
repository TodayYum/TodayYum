import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faHeart, faPencil } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import Header from '../atoms/Header';
import SelectScore from '../atoms/SelectScore';
import CommentContainer from '../atoms/CommentContainer';
import CommentList from '../organisms/CommenetList';
import CommentInput from '../atoms/CommentInput';

function FilmDetailPage() {
  const fixedContentsRef = useRef<HTMLDivElement>(null);
  const [height, setHeight] = useState(490);
  const [isMainContents, setIsMainContents] = useState(true);
  const [commentInputValue, setCommentInputValue] = useState('');
  const navigate = useNavigate();
  const resizeObserver = new ResizeObserver(entries => {
    // 하나만 observe할 것이므로 forEach를 사용하지 않는다.
    if (entries[0].target === fixedContentsRef.current) {
      setHeight(entries[0].contentRect.height);
    }
  });
  console.log(setIsMainContents);
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
  return (
    <div>
      <Header title="asdf" ateAt="asdf" />
      {/* 상단부 */}
      <div className="fixed overflow-hidden top-[58px]" ref={fixedContentsRef}>
        <DetailPageCarousel
          imgs={['/t1.jpg', '/t2.jpg', '/t3.jpg', '/t4.jpg']}
        />
        {/* 프로필 */}
        <div className="bg-white flex justify-between p-[15px]">
          <div className="flex items-center gap-2">
            <img
              src="/t3.jpg"
              alt="프로필사진"
              className="rounded-full h-7 w-7"
            />
            <span className="base-bold my-auto">YounRi</span>
          </div>
          <div>
            <FontAwesomeIcon icon={faHeart} className="text-error mr-2" />
            <span className="text-base">35</span>
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
          <p className="text-base my-[15px]">
            김ㅁㅁㅁㅁㅁㅁ치말이ㅣㅣㅣㅣ 완전 조ㅗㅗㅗㅗㅗㅗㅗㅗ아
            ㄹㄹㄹㄹㄹㄹㄹㄹㄹfffffff
            ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ
            ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ
            ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ
          </p>
          {DUMMY_TAGLIST.map(element => (
            <span className="text-base mr-2"># {element}</span>
          ))}
          <p className="text-base my-5">서울특별시 강남구 테헤란로 212</p>
          <SelectScore score={[0, 0, 0]} />
        </div>
      ) : (
        <CommentList
          onClick={() => setIsMainContents(true)}
          marginTop={height}
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
            nickname="닉네임"
            comment="내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용"
            isPreview
          />
        </div>
      ) : (
        <CommentInput value={commentInputValue} onChange={handleChange} />
      )}
    </div>
  );
}

const DUMMY_TAGLIST = [
  '첫번째 태그',
  '두번째 태그',
  '세번째 태그',
  '네번째 태그',
];

const DUMMY_ISMINE = true;
export default FilmDetailPage;
