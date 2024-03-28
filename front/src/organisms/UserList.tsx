import SearchAccountContainer from './SearchAccountContainer';

function UserList() {
  const userList = DUMMY_ACCOUNT;
  return (
    <div className="px-[30px] mt-2 flex flex-col gap-5">
      {userList.map(element => (
        <SearchAccountContainer
          comment={element.comment}
          imgSrc={element.imgSrc}
          nickname={element.nickname}
        />
      ))}
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

export default UserList;
