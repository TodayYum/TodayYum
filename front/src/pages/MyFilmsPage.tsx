/**
 * WriteFilmsPage
 * 글 목록 페이지
 * @returns
 */

import { useLocation } from 'react-router-dom';
// import Header from '../atoms/Header';

import PolaroidList from '../organisms/PolaroidList';
// import { IMyFilmsPage } from '../types/pages/MyFilmsPage.types';

function MyFilmsPage() {
  const location = useLocation();
  const { filmList } = location.state;
  return (
    <div>
      <PolaroidList polaroidList={filmList} />
    </div>
  );
}

export default MyFilmsPage;
