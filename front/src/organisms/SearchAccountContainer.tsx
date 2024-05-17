import { Link } from 'react-router-dom';
import { ISearchAccountContainer } from '../types/organisms/SearchAccountContainer.types';

function SearchAccountContainer(props: ISearchAccountContainer) {
  return (
    <Link
      to="/recent"
      className="rounded bg-white flex px-10 py-4 justify-between gap-5"
    >
      <div>
        <p className="base-bold">{props.nickname}</p>
        <p className="text-base line-clamp-1">{props.comment}</p>
      </div>
      <img
        src={props.imgSrc}
        alt="프로필 사진"
        className="bg-white h-[60px] w-[60px] rounded-full"
      />
    </Link>
  );
}

export default SearchAccountContainer;
