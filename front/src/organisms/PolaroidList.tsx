import { IPolaroidList } from '../types/organisms/PolaroidList.types';
import PolaroidFilm from './PolaroidFilm';

function PolaroidList(props: IPolaroidList) {
  return (
    <div className="flex justify-start justify-items-start flex-wrap gap-4 mx-[30px] mt-5 w-auto">
      {props.polaroidList.map(polaroid => (
        <PolaroidFilm
          tag={polaroid.tag}
          thumbnail={polaroid.thumbnail}
          id={polaroid.id}
          totalScore={polaroid.totalScore}
          yummyCount={polaroid.yummyCount}
          key={polaroid.id}
          category={polaroid.category}
        />
      ))}
      <div ref={props.setRef} className="opacity-00">
        now loading
      </div>
    </div>
  );
}

export default PolaroidList;
