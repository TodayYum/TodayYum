import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState, useRef, useEffect } from 'react';
import { IDetailPageCarousel } from '../types/components/DetailPageCarousel.types';

function DetailPageCarousel(props: IDetailPageCarousel) {
  const [carouselIdx, setCarouselIdx] = useState<number>(0);
  const [width, setWidth] = useState(393);
  const carouselRef = useRef<HTMLDivElement>(null);
  const handleChevronClick = (direction: number) => {
    if (
      carouselIdx + direction < 0 ||
      carouselIdx + direction === props.imgs.length
    )
      return;
    setCarouselIdx(prev => prev + direction);
  };

  const resizeObserver = new ResizeObserver(entries => {
    // 하나만 observe할 것이므로 forEach를 사용하지 않는다.
    if (entries[0].target === carouselRef.current) {
      setWidth(entries[0].contentRect.width);
    }
  });

  const getCarouselWidth = () => {
    console.log('변화감지', props.imgWidth);
    if (width <= 393) {
      return `${393 * carouselIdx}px`;
    }
    return `${carouselIdx * 100}vw`;
  };

  useEffect(() => {
    if (carouselRef.current) {
      resizeObserver.observe(carouselRef.current);
    }

    return () => {
      resizeObserver.disconnect();
    };
  }, [carouselRef.current]);
  return (
    <div
      className={`w-[100vw] sm:w-[393px] ${props.customCSS} bg-white`}
      ref={carouselRef}
    >
      <div
        className="flex transition duration-1000"
        style={{ transform: `translate(-${getCarouselWidth()})` }}
      >
        {props.imgs.map(element => (
          <div className="flex-[0_0_100vw] sm:flex-[0_0_393px]">
            <img
              src={element}
              alt="#"
              className="max-h-[473px] h-[110vw] sm:h-[393px] object-cover"
              style={{ width }}
            />
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
