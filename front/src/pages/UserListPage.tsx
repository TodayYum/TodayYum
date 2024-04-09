import { useLocation } from 'react-router-dom';
import UserList from '../organisms/UserList';

function UserListPage() {
  const location = useLocation();
  const { userList } = location.state;
  console.log(userList);
  return (
    <div>
      <UserList />
    </div>
  );
}

export default UserListPage;
