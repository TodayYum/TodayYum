import useCreateFilmAtom from '../jotai/createFilm';

const Img = ({ src, isFirst }: { src: string; isFirst: boolean }) => {
  return (
    <img
      src={src}
      alt="썸네일"
      className={`${isFirst ? 'h-[180px] w-[180px]' : 'h-[70px] w-[70px]'}  rounded-2xl object-cover`}
    />
  );
};

function ThumbnailList() {
  const [createFilm] = useCreateFilmAtom();
  return (
    <div className="flex gap-2 items-end w-[calc(100%-30px)] overflow-auto m-[15px] mb-[30px]">
      {createFilm.imagesURL.map((element, idx) => (
        <Img src={element} isFirst={!idx} />
      ))}
    </div>
  );
}

export default ThumbnailList;
