/**
 * 하단 NavBar 포함하는 Layout
 * @returns
 */

import { Outlet } from 'react-router-dom';
import Navbar from '../atoms/Navbar';
import SearchBar from '../atoms/SearchBar';

function SearchLayout() {
  return (
    <div>
      <SearchBar />
      <Outlet />
      <Navbar />
    </div>
  );
}

export default SearchLayout;
