// import { useLocation } from 'react-router-dom';
import { useState } from 'react';
import UserList from '../organisms/UserList';
import useIntersect from '../util/useIntersect';
import { IUserThumbnail } from '../types/organisms/UserList';

function UserListPage() {
  // const location = useLocation();
  const [userList, setUserList] = useState<IUserThumbnail[]>(DUMMY_ACCOUNT);
  // const { userList } = location.state;
  const [, setRef] = useIntersect(async (entry, observer) => {
    // API 결과에 따라 hasNext 결정
    const hasNext = true;
    const newList = userList.concat(JSON.parse(JSON.stringify(userList)));
    setUserList(newList);
    observer.unobserve(entry.target);
    return hasNext;
  }, {});
  console.log(userList);
  return (
    <div>
      <UserList userList={userList} setRef={setRef} />
    </div>
  );
}
const DUMMY_ACCOUNT = [
  {
    nickname: '닉네임',
    imgSrc: '/logo.svg',
    comment: '테스트입니다.',
  },
  {
    nickname: '닉네임',
    imgSrc: '/logo.svg',
    comment: '테스트입니다.',
  },
  {
    nickname: '닉네임',
    imgSrc: '/logo.svg',
    comment: '테스트입니다.',
  },
  {
    nickname: '닉네임',
    imgSrc: '/logo.svc',
    comment: '테스트입니다.',
  },
  {
    nickname: '닉네임',
    imgSrc: '/logo.svc',
    comment: '테스트입니다.',
  },
];
export default UserListPage;
