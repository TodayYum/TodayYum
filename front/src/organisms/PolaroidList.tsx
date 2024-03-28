import { IPolaroidList } from '../types/organisms/PolaroidList.types';
import PolaroidFilm from './PolaroidFilm';

function PolaroidList(props: IPolaroidList) {
  return (
    <div className="flex justify-start justify-items-start flex-wrap gap-4 mx-[30px] mt-5 w-auto">
      {props.polaroidList.map(polaroid => (
        <PolaroidFilm
          firstTag={polaroid.firstTag}
          imgSrc={polaroid.imgSrc}
          linkPage={polaroid.linkPage}
          score={polaroid.score}
          yummyCount={polaroid.yummyCount}
          key={polaroid.linkPage}
        />
      ))}
    </div>
  );
}

export default PolaroidList;
