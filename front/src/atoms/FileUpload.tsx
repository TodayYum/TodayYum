import { useRef } from 'react';
import useCreateFilmAtom from '../jotai/createFilm';

function FileUpload() {
  const [createFilmData, setCreateFilmData] = useCreateFilmAtom();
  const inputRef = useRef<HTMLInputElement>(null);
  const handleFictureChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log('이거되나', e);
    if (!e.target.files) return;
    console.log(e.target.files, '테스트');
    setCreateFilmData({ files: [e.target.files[0]] });
  };

  const handleClick = () => {
    if (!inputRef.current) return;
    console.log('시발');
    inputRef.current.click();
  };
  console.log(createFilmData, handleFictureChange);
  return (
    <div>
      <input
        className="mt-[100px]"
        type="file"
        multiple
        // accept="image/*"
        onChange={handleFictureChange}
        ref={inputRef}
      />
      <button type="button" onClick={handleClick}>
        제발업로드
      </button>
    </div>
  );
}

export default FileUpload;
