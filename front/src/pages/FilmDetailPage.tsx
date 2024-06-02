import { useRef, useState, useEffect, useMemo } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faClose,
  faHeart,
  faPencil,
  faStar,
} from '@fortawesome/free-solid-svg-icons';
import { faHeart as faEmptyHeart } from '@fortawesome/free-regular-svg-icons';
import { useNavigate, useParams } from 'react-router-dom';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import Header from '../atoms/Header';
import SelectScore from '../atoms/SelectScore';
import CommentContainer from '../atoms/CommentContainer';
import CommentList from '../organisms/CommenetList';
import CommentInput from '../atoms/CommentInput';
import {
  fetchDeleteYummy,
  fetchGetBoardDetail,
  fetchPostAddYummy,
  fetchDeleteFilm,
} from '../services/boardService';
import { CATEGORY_LIST, CATEGORY_MAP } from '../constant/searchConstant';
import { ISOtoLocal } from '../util/dateUtil';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';
import { fetchGetCommentList } from '../services/commentService';
import { ICommentReponse } from '../types/organisms/CommentList.types';
import { IPageableResponse } from '../types/services/boardService';
import { TIME_LIST, TIME_MAP } from '../constant/createFilmConstant';
import { useInitFilmAtom } from '../jotai/updateFilm';
import useSignInDataAtom from '../jotai/signInData';

function FilmDetailPage() {
  const queryClient = useQueryClient();
  const [signInData] = useSignInDataAtom();
  const fixedContentsRef = useRef<HTMLDivElement>(null);
  const { boardId } = useParams();
  const [height, setHeight] = useState(490);
  const [isMainContents, setIsMainContents] = useState(true);
  const [commentInputValue, setCommentInputValue] = useState('');
  const navigate = useNavigate();
  const initUpdateFilm = useInitFilmAtom();
  const [, setRef, commentResponse, , refetchCommentList] =
    useInfiniteQueryProduct(
      {
        constant: 'commentList',
        variables: [],
        stringVariables: [boardId ?? '0'],
      },
      () => fetchGetCommentList,
      {},
    );

  const commentList: ICommentReponse[] = useMemo(() => {
    const result: ICommentReponse[] = [];
    if (commentResponse === undefined) return result;
    (commentResponse as IPageableResponse[]).forEach(page =>
      (page.content as ICommentReponse[]).forEach(element =>
        result.push(element),
      ),
    );
    return result;
  }, [commentResponse]);

  const { mutate: controlYummy } = useMutation({
    mutationFn: (yummy: boolean) =>
      yummy
        ? fetchDeleteYummy(boardId ?? '0')
        : fetchPostAddYummy(boardId ?? '0'),
    onSuccess: () => {
      refetch();
    },
  });

  const { mutate: deleteFilm } = useMutation({
    mutationFn: () => fetchDeleteFilm(boardId ?? '0'),
    onSuccess: res => {
      console.log('삭제 성공', res);
      queryClient.invalidateQueries({ queryKey: ['boardList'] });
      navigate('/');
    },
  });
  const { data, isSuccess, refetch } = useQuery({
    queryKey: ['boardDetail', boardId],
    queryFn: () => fetchGetBoardDetail(boardId ?? '0'),
    staleTime: 500000,
  });

  const handleClickYummy = () => {
    controlYummy(data.yummy);
  };

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
        ateAt={`${ISOtoLocal(data.ateAt)} ${TIME_LIST[TIME_MAP[data.mealTime]].kr}`}
      />
      {/* 상단부 */}
      <div className="fixed overflow-hidden top-[58px]" ref={fixedContentsRef}>
        <DetailPageCarousel imgs={data.images} />
        {/* 프로필 */}
        <div className="bg-white flex justify-between p-[15px]">
          <div
            className="flex items-center gap-2"
            onClick={() =>
              navigate('/profile', { state: { memberId: data.memberId } })
            }
            role="button"
            onKeyUp={() => {}}
            tabIndex={0}
          >
            <img
              src={data.profile ?? '/default_profile.png'}
              alt="프로필사진"
              className="rounded-full h-7 w-7"
            />
            <span className="base-bold my-auto">{data.nickname}</span>
          </div>
          <div>
            <FontAwesomeIcon
              icon={data.yummy ? faHeart : faEmptyHeart}
              className="text-error mr-2"
              onClick={handleClickYummy}
            />
            <span className="text-base">{data.yummyCount}</span>
            {data.memberId === signInData.memberId && (
              <FontAwesomeIcon
                icon={faPencil}
                className="ml-2 text-gray-dark"
                onClick={() => {
                  navigate('/edit-film', { state: { board: data } });
                  initUpdateFilm(data);
                }}
              />
            )}
            {data.memberId === signInData.memberId && (
              <FontAwesomeIcon
                icon={faClose}
                className="ml-2 text-gray-dark text-xl"
                onClick={() => deleteFilm()}
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
          <div className="flex flex-row justify-between mt-[15px]">
            {/* <p className="text-base my-5">서울특별시 강남구 테헤란로 212</p> */}
            <SelectScore
              score={[data.tasteScore, data.priceScore, data.moodScore]}
              customCSS="w-[280px]"
            />
            <span className="text-xl font-bold self-end">
              <FontAwesomeIcon
                icon={faStar}
                className="text-3xl text-error mr-1"
              />
              {data.totalScore}
            </span>
          </div>
        </div>
      ) : (
        <CommentList
          commentList={commentList}
          onClick={() => setIsMainContents(true)}
          marginTop={height}
          boardId={Number(data.id ?? '0')}
          setRef={setRef}
          refetch={refetchCommentList}
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
            commentId={0}
            nickname={
              data.commentWriter ??
              `${localStorage.getItem('nickname') ?? ''} 님`
            }
            memberId=""
            modifiedAt=""
            profile=""
            comment={data.comment ?? '첫 댓글을 입력해주세요!'}
            isPreview
          />
        </div>
      ) : (
        <CommentInput
          value={commentInputValue}
          onChange={handleChange}
          boardId={boardId ?? '0'}
          refetch={refetchCommentList}
        />
      )}
    </div>
  );
}

export default FilmDetailPage;
