import { useRef, useState, useEffect } from 'react';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faUpload } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCamera } from '@fortawesome/free-solid-svg-icons';
import DetailPageCarousel from '../atoms/DetailPageCarousel';
import HasNextHeader from '../atoms/HasNextHeader';
import useCreateFilmAtom from '../jotai/createFilm';
import RectangleButton from '../atoms/RectangleButton';

function UploadFicturesPage() {
  const [createFilmData, setCreateFilmData] = useCreateFilmAtom();
  const [carouselRef, uploadRef] = [
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
  ];
  const [height, setHeight] = useState(490);

  const resizeObserver = new ResizeObserver(entries => {
    if (entries[0].target === carouselRef.current) {
      setHeight(entries[0].contentRect.height + 58);
    }
  });

  const handleFictureChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files?.length) {
      setCreateFilmData({ images: [], imagesURL: [] });
      return;
    }
    const fileList = Array.from(e.target.files);
    const fileURLList = fileList.map(element =>
      window.URL.createObjectURL(element),
    );
    setCreateFilmData({ images: fileList, imagesURL: fileURLList });
  };

  const handleClickUploadButton = () => {
    uploadRef.current?.click();
  };
  useEffect(() => {
    if (carouselRef.current) {
      resizeObserver.observe(carouselRef.current);
    }

    return () => {
      resizeObserver.disconnect();
    };
  }, [carouselRef.current]);

  return (
    <div className="relative">
      <HasNextHeader isActive={createFilmData.imagesURL.length > 0} />
      {createFilmData.imagesURL.length === 0 && (
        <div
          className="fixed top-[58px] w-full min-w-[393px] bg-white max-h-[473px] h-[110vw] flex justify-center"
          onClick={handleClickUploadButton}
          tabIndex={0}
          onKeyUp={() => {}}
          role="button"
          ref={carouselRef}
        >
          <div className="flex flex-col justify-center items-center">
            <FontAwesomeIcon
              icon={faCamera}
              className="text-[80px] text-secondary "
            />
            <p className="base-bold mt-4">선택한 사진이 없습니다.</p>
          </div>
        </div>
      )}
      {createFilmData.imagesURL.length > 0 && (
        <DetailPageCarousel
          imgs={createFilmData.imagesURL}
          customCSS="fixed top-[58px]"
        />
      )}
      <label htmlFor="tete">
        <div style={{ marginTop: height }} ref={uploadRef}>
          <RectangleButton
            onClick={handleClickUploadButton}
            text="업로드할 사진 선택하기"
            customClass="w-[calc(100%-60px)] m-[30px] min-w-[333px]"
          />
        </div>
        <input
          className="mt-[100px] hidden"
          type="file"
          multiple
          accept="image/*"
          onChange={handleFictureChange}
          id="tete"
        />
      </label>
    </div>
  );
}

export default UploadFicturesPage;
