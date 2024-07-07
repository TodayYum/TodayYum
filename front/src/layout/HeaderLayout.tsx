import { Outlet, useLocation } from 'react-router-dom';
import Header from '../atoms/Header';

function HeaderLayout() {
  const location = useLocation();
  return (
    <div>
      <Header title={location.state.title} />
      <Outlet />
    </div>
  );
}

export default HeaderLayout;
