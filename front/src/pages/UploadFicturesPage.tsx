import FileUpload from '../atoms/FileUpload';
import Header from '../atoms/Header';
// import useCreateFilmAtom from '../jotai/createFilm';

function UploadFicturesPage() {
  //   const [createFilmData, setCreateFilmData] = useCreateFilmAtom();

  //   const handleFictureChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  //     console.log('이거되나', e);
  //     if (!e.target.files) return;
  //     console.log(e.target.files, '테스트');
  //     setCreateFilmData({ files: [e.target.files[0]] });
  //   };

  //   //   const handleClick = (e: React.MouseEvent<HTMLInputElement>) => {
  //   //     e.currentTarget.click();
  //   //   };
  //   console.log(createFilmData, handleFictureChange);
  return (
    <div>
      <Header title="글 쓰기" />
      <FileUpload />
    </div>
  );
}

export default UploadFicturesPage;
