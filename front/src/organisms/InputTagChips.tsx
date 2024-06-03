import { useState } from 'react';
import InputText from '../atoms/InputText';
import RectangleButton from '../atoms/RectangleButton';
import DeletableChip from '../atoms/DeletableChip';
import { useUpdateFilmAtom, useUpdateFilmValueAtom } from '../jotai/updateFilm';

function InputTagChips() {
  const updateAtom = useUpdateFilmValueAtom();
  const setUpdateAtom = useUpdateFilmAtom();
  const [tagName, setTagName] = useState('');

  const handleTagInput = (input: string) => {
    setTagName(input);
  };
  const handleAddTagButton = () => {
    const prevTags = [...updateAtom.tags];
    if (prevTags.length === 10) return;
    prevTags.push(tagName);
    setUpdateAtom({ tags: prevTags });
    setTagName('');
  };
  const handleDeleteTag = (index: number) => {
    const tags = [...updateAtom.tags];
    tags.splice(index, 1);
    setUpdateAtom({ tags });
  };
  return (
    <div>
      <div className="flex flex-row gap-4 items-center mx-[15px] mb-[30px]">
        <InputText
          placeholder="첫번째 태그가 썸네일에 나타납니다."
          type="text"
          setValue={handleTagInput}
          hasSupport={false}
          customClass="flex-[1_0_60%]"
          value={tagName}
        />
        <RectangleButton
          onClick={handleAddTagButton}
          text="추가"
          customClass="w-[70px] !h-[40px]"
        />
      </div>
      <div className="flex flex-wrap m-[15px] gap-2 mb-[30px]">
        {updateAtom.tags.map((element, idx) => (
          <DeletableChip
            dataId=""
            deleteSearchWord={() => handleDeleteTag(idx)}
            text={`# ${element}`}
            onSelectClick={() => {}}
            key={element}
          />
        ))}
      </div>
    </div>
  );
}

export default InputTagChips;
