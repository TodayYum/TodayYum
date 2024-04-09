import { Outlet, useLocation } from 'react-router-dom';
import Header from '../atoms/Header';

function HeaderLayout() {
  const location = useLocation();
  console.log(location, '레이아웃의 로케이션 확인');
  return (
    <div>
      <Header title={location.state.title} />
      <Outlet />
    </div>
  );
}

export default HeaderLayout;
