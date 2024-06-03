import { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import Header from '../atoms/Header';
import ThumbnailList from '../organisms/ThumbnailList';
import CreateFilmFirstData from '../organisms/CreateFilmFirstData';
import CreateFilmSecondData from '../organisms/CreateFilmSecondData';
import RectangleButton from '../atoms/RectangleButton';
import useCreateFilmAtom from '../jotai/createFilm';
import { fetchPostAddBoard } from '../services/boardService';

function CreatePolaroidFilmPage() {
  const [isFirst, setIsFirst] = useState(true);
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const [createFilm] = useCreateFilmAtom();
  const { mutate } = useMutation({
    mutationFn: () => fetchPostAddBoard(createFilm),
    onSuccess: () => {
      queryClient.removeQueries({ queryKey: ['boardList'] });
      navigate('/');
    },
  });
  const handleFirstNextButton = () => {
    if (!createFilm.images.length || createFilm.category === -1) {
      // Swal 자리
      console.log('선택하지 않은 항목이 있습니다.');
      return;
    }
    setIsFirst(false);
  };

  const handleSecondNextButton = () => {
    if (!createFilm.content.length || !createFilm.tags.length) {
      // Swal 자리
      console.log('선택하지 않은 항목이 있습니다.');
    }
    mutate();
  };

  return (
    <div className="min-w-[393px]">
      <Header title="글 작성" />
      <ThumbnailList />
      {isFirst ? <CreateFilmFirstData /> : <CreateFilmSecondData />}
      {isFirst ? (
        <RectangleButton
          text="다음으로"
          onClick={handleFirstNextButton}
          customClass="w-[calc(100%-30px)] fixed bottom-20 left-1/2 -translate-x-[50%]"
        />
      ) : (
        <div className="flex flex-row gap-4 fixed bottom-20 w-[calc(100%-30px)] -translate-x-[50%] left-1/2">
          <RectangleButton
            text="등록하기"
            onClick={handleSecondNextButton}
            customClass="w-full"
          />
          <RectangleButton
            text="취소"
            onClick={() => setIsFirst(true)}
            isCancle
            customClass="w-full"
          />
        </div>
      )}
    </div>
  );
}

export default CreatePolaroidFilmPage;
