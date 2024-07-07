import { IUserList } from '../types/organisms/UserList';
import SearchAccountContainer from './SearchAccountContainer';

function UserList(props: IUserList) {
  return (
    <div className="px-[30px] mt-2 flex flex-col gap-5">
      {props.userList.map(element => (
        <SearchAccountContainer
          comment={element.comment}
          imgSrc={element.profile}
          nickname={element.nickname}
          key={element.nickname}
        />
      ))}
      <div ref={props.setRef} className="opacity-0">
        now loading
      </div>
    </div>
  );
}

export default UserList;
