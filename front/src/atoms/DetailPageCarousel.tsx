import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import { IDetailPageCarousel } from '../types/components/DetailPageCarousel.types';

function DetailPageCarousel(props: IDetailPageCarousel) {
  const [carouselIdx, setCarouselIdx] = useState<number>(0);
  const handleChevronClick = (direction: number) => {
    if (
      carouselIdx + direction < 0 ||
      carouselIdx + direction === props.imgs.length
    )
      return;
    setCarouselIdx(prev => prev + direction);
  };
  return (
    <div className={`w-[100vw] ${props.customCSS}`} ref={props.divRef}>
      <div
        className="flex transition duration-1000"
        style={{ transform: `translate(-${carouselIdx * 100}vw)` }}
      >
        {props.imgs.map(element => (
          <div className="flex-[0_0_100vw]">
            <img src={element} alt="#" className=" h-[110vw] object-cover" />
          </div>
        ))}
      </div>

      <div className="absolute top-[50%] -translate-y-[50%] flex justify-between w-full px-4">
        <div className="rounded-full bg-gray-dark/30 w-10 h-12 flex justify-center items-center">
          <FontAwesomeIcon
            icon={faChevronLeft}
            onClick={() => handleChevronClick(-1)}
            className="text-4xl text-white/70"
          />
        </div>

        <div className="rounded-full bg-gray-dark/30 w-10 h-12 flex justify-center items-center">
          <FontAwesomeIcon
            icon={faChevronRight}
            onClick={() => handleChevronClick(1)}
            className="text-4xl text-white/70"
          />
        </div>
      </div>
    </div>
  );
}

export default DetailPageCarousel;
