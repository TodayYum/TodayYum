import { useRef, useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import Header from '../atoms/Header';
import SelectScore from '../atoms/SelectScore';

function FilmDetailPage() {
  const fixedContentsRef = useRef<HTMLDivElement>(null);
  const [height, setHeight] = useState(490);

  const resizeObserver = new ResizeObserver(entries => {
    // 하나만 observe할 것이므로 forEach를 사용하지 않는다.
    if (entries[0].target === fixedContentsRef.current) {
      setHeight(entries[0].contentRect.height + 58);
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
  return (
    <div>
      <Header title="asdf" ateAt="asdf" />
      <div className="fixed overflow-hidden top-[58px]" ref={fixedContentsRef}>
        <DetailPageCarousel
          imgs={['/t1.jpg', '/t2.jpg', '/t3.jpg', '/t4.jpg']}
        />
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
          </div>
        </div>
      </div>
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
        <SelectScore />
      </div>
      <div className="flex justify-between p-3 gap-2 bg-white rounded mx-[15px] fixed bottom-20 w-[calc(100vw-30px)] shadow-lg">
        <span className="font-bold text-sm flex-[0_0_110px]">닉네임</span>
        <span className="text-sm flex-[1_1_60%] line-clamp-1">
          내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용내용용용용용
        </span>
      </div>
      <div className="bg-black h-36 invisible ">asdf</div>
    </div>
  );
}

const DUMMY_TAGLIST = [
  '첫번째 태그',
  '두번째 태그',
  '세번째 태그',
  '네번째 태그',
];

export default FilmDetailPage;
