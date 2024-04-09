import { useEffect, useRef, useState } from 'react';
import ResetPassword from './ResetPassword';
import EditProfileFirstContainer from './EditProfileBasicInfoContainer';
import EditProfileIdentifyUserContainer from './EditProfileIdentifyUserContainer';

function EditProfileModal({ onClose }: { onClose: () => void }) {
  const modalRef = useRef<HTMLDivElement>(null);
  const modal2Ref = useRef<HTMLDivElement>(null);

  const [modalLevel, setModalLevel] = useState(0);

  const handleModalClose = (e: MouseEvent) => {
    if (
      modalRef.current &&
      !modalRef.current.contains(e.target as Node) &&
      modal2Ref.current?.contains(e.target as Node)
    ) {
      onClose();
    }
  };

  const handleModalLevel = (input: number) => {
    setModalLevel(input);
  };

  const modalArr = [
    <EditProfileFirstContainer goNextLevel={() => setModalLevel(1)} />,
    <EditProfileIdentifyUserContainer goNextLevel={() => setModalLevel(2)} />,
    <ResetPassword setModalLevel={handleModalLevel} />,
  ];

  useEffect(() => {
    document.addEventListener('mousedown', handleModalClose);
    return () => document.removeEventListener('mousedown', handleModalClose);
  }, []);

  return (
    <div
      className="bg-black/30 w-screen h-[100%] absolute top-0 left-0"
      ref={modal2Ref}
    >
      <div
        ref={modalRef}
        className="rounded-2xl bg-white shadow-lg p-[15px] absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 w-10/12"
      >
        {modalArr[modalLevel]}
      </div>
    </div>
  );
}

export default EditProfileModal;
