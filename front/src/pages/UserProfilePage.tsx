/**
 * UserProfilePage : 유저데이터 페이지 조회(마이페이지)
 *
 */

// import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronRight } from '@fortawesome/free-solid-svg-icons';
import Header from '../atoms/Header';
import UserProfileContainer from '../organisms/UserProfileContainer';
import PolaroidFilm from '../organisms/PolaroidFilm';
import { IPolaroidFilm } from '../types/organisms/PolaroidFilm.types';
// import { IUserProfileContainer } from '../types/organisms/UserProfileContainer.types';

function UserProfilePage() {
  // const [userProfile] = useState<IUserProfileContainer>(DUMMY_PROFILE);
  const navigate = useNavigate();

  const handleGoMyFilms = () => {
    navigate('/write-list', {
      state: {
        title: '작성한 글',
        filmList: DUMMY_FILMS,
      },
    });
  };

  const handleGoYummyList = () => {
    navigate('/yummy-list', {
      state: {
        title: 'Yummy 목록',
        filmList: DUMMY_FILMS,
      },
    });
  };
  return (
    <div>
      <Header title="마이페이지" />
      <UserProfileContainer
      // comment={userProfile.comment}
      // followerCount={userProfile.followerCount}
      // followingCount={userProfile.followingCount}
      // nickname={userProfile.nickname}
      // profile={userProfile.profile}
      />
      <div
        className="flex justify-between mx-[15px]"
        onClick={handleGoMyFilms}
        tabIndex={0}
        onKeyUp={() => {}}
        role="button"
      >
        <p className="base-bold ml-2">작성한 글</p>
        <FontAwesomeIcon icon={faChevronRight} />
      </div>
      <div className="flex w-[calc(100%-30px)] overflow-auto gap-4 mx-[15px]">
        {DUMMY_FILMS.map(element => (
          <PolaroidFilm
            firstTag={element.firstTag}
            imgSrc={element.imgSrc}
            linkPage={element.linkPage}
            score={element.score}
            yummyCount={element.yummyCount}
          />
        ))}
      </div>
      <div
        className="flex justify-between mx-[15px] mt-[15px]"
        onClick={handleGoYummyList}
        tabIndex={0}
        onKeyUp={() => {}}
        role="button"
      >
        <p className="base-bold ml-2">Yummy 목록</p>
        <FontAwesomeIcon icon={faChevronRight} />
      </div>
      <div className="flex w-[calc(100%-30px)] overflow-auto gap-4 mx-[15px]">
        {DUMMY_FILMS.map(element => (
          <PolaroidFilm
            firstTag={element.firstTag}
            imgSrc={element.imgSrc}
            linkPage={element.linkPage}
            score={element.score}
            yummyCount={element.yummyCount}
          />
        ))}
      </div>
    </div>
  );
}

// const DUMMY_PROFILE = {
//   email: 'test@test.com',
//   nickname: 'yonggkimm',
//   comment: 'ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ',
//   profile: '/t3.jpg',
//   followerCount: 10000,
//   followingCount: 1,
// };

const DUMMY_FILMS: IPolaroidFilm[] = [
  {
    firstTag: '테스트테스트테스트테스트테스트테스트테스트테스트테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
  {
    firstTag: '테스트',
    imgSrc: '/logo.svg',
    score: 4.5,
    yummyCount: 30,
    linkPage: '/recent',
  },
];

export default UserProfilePage;
