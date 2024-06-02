import { useLocation } from 'react-router-dom';
import { useMemo } from 'react';
import UserList from '../organisms/UserList';
import { IUserThumbnail } from '../types/organisms/UserList';
import {
  fetchGetFollowerList,
  fetchGetFollowingList,
} from '../services/userService';
import useInfiniteQueryProduct from '../util/useInfiniteQueryProduct';
import { IPageableResponse } from '../types/services/boardService';

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
    const result: IUserThumbnail[] = [];
    (data as IPageableResponse[])?.forEach(page =>
      (page.content as IUserThumbnail[]).forEach(user => result.push(user)),
    );
    return result as IUserThumbnail[];
  }, [data]);
  return (
    <div>{!isPending && <UserList userList={product} setRef={setRef} />}</div>
  );
}

export default UserListPage;
