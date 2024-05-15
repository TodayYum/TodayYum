import { useLocation } from 'react-router-dom';
import { useMemo } from 'react';
import UserList from '../organisms/UserList';
import { IUserThumbnail } from '../types/organisms/UserList';
import {
  fetchGetFollowerList,
  fetchGetFollowingList,
} from '../services/userService';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';

function UserListPage() {
  const locationState = useLocation().state;
  const [, setRef, data, isPending] = useInfiniteQueryProduct(
    {
      constant: locationState.type,
      variables: [],
      stringVariables: [locationState.memberId],
    },
    () =>
      locationState.type === 'following'
        ? fetchGetFollowingList
        : fetchGetFollowerList,
    {},
  );

  const product: IUserThumbnail[] = useMemo(() => {
    console.log('우에에에', data, locationState);
    return data as IUserThumbnail[];
  }, [data]);
  return (
    <div>{!isPending && <UserList userList={product} setRef={setRef} />}</div>
  );
}

export default UserListPage;
