import { ChangeEvent, useState } from 'react';
import DeletableChip from '../atoms/DeletableChip';
import InputText from '../atoms/InputText';
import useCreateFilmAtom from '../jotai/createFilm';
import RectangleButton from '../atoms/RectangleButton';

function CreateFilmSecondData() {
  const [creatFilm, setCreateFilm] = useCreateFilmAtom();
  const [tagName, setTagName] = useState('');
  const handleTextArea = (e: ChangeEvent<HTMLTextAreaElement>) => {
    if (e.target.value.length > 100) return;
    setCreateFilm({ contents: e.target.value });
  };
  console.log(tagName);
  const handleTagInput = (input: string) => {
    setTagName(input);
  };
  const handleAddTagButton = () => {
    const prevTags = [...creatFilm.tags];
    if (prevTags.length === 10) return;
    prevTags.push(tagName);
    setCreateFilm({ tags: prevTags });
    setTagName('');
  };

  const handleDeleteTag = (index: number) => {
    const tags = [...creatFilm.tags];
    tags.splice(index, 1);
    setCreateFilm({ tags });
  };
  return (
    <div>
      <p className="base-bold mx-[15px]">
        식사평을 적어주세요. ({creatFilm.contents.length}/100)
      </p>
      <textarea
        className="focus:outline-secondary focus:outline-1 border-gray-dark border-[1px] rounded h-20 mx-[15px] mb-[30px] w-[calc(100%-30px)]"
        onChange={handleTextArea}
        value={creatFilm.contents}
      />
      <p className="base-bold mx-[15px]">
        태그를 추가해주세요. ({creatFilm.tags.length}/10)
      </p>
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
      <div className="flex flex-wrap m-[15px] gap-2 mb-[150px]">
        {creatFilm.tags.map((element, idx) => (
          <DeletableChip
            dataId=""
            deleteSearchWord={() => handleDeleteTag(idx)}
            text={`# ${element}`}
            onSelectClick={() => {}}
          />
        ))}
      </div>
    </div>
  );
}

export default CreateFilmSecondData;
