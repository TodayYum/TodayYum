/**
 * 하단 NavBar 포함하는 Layout
 * @returns
 */

import { Outlet } from 'react-router-dom';
import Navbar from '../atoms/Navbar';

function NavBarLayout() {
  return (
    <div>
      <Outlet />
      <Navbar />
    </div>
  );
}

export default NavBarLayout;
