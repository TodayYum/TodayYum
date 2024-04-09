import './App.css';
import { Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import ResetPasswordPage from './pages/ResetPasswordPage';
import HeaderOnlyLayout from './layout/HeaderOnlyLayout';
import MainPage from './pages/MainPage';
import NavBarLayout from './layout/NavBarLayout';
import SearchLayout from './layout/SearchLayout';
import RecentSearchPage from './pages/RecentSearchPage';
import SearchResultPage from './pages/SearchResultPage';
import FilmDetailPage from './pages/FilmDetailPage';
import UploadFicturesPage from './pages/UploadFicturesPage';
import CreatePolaroidFilmPage from './pages/CreatePolaroidFilmPage';
import UserProfilePage from './pages/UserProfilePage';
// import MyFilmsPage from './pages/MyFilmsPage';
import HeaderLayout from './layout/HeaderLayout';
import MyFilmsPage from './pages/MyFilmsPage';
import UserListPage from './pages/UserListPage';

function App() {
  return (
    <Routes>
      <Route element={<NavBarLayout />}>
        <Route path="/" element={<MainPage />} />
        <Route path="/test" element={<FilmDetailPage />} />
        <Route path="/profile" element={<UserProfilePage />} />
      </Route>
      <Route path="/create-board" element={<NavBarLayout />}>
        <Route path="select-fictures" element={<UploadFicturesPage />} />
        <Route path="" element={<CreatePolaroidFilmPage />} />
      </Route>
      <Route path="/login" element={<LoginPage />} />
      <Route element={<HeaderOnlyLayout isSignUp />}>
        <Route path="/sign-up" element={<SignUpPage />} />
      </Route>
      <Route element={<HeaderOnlyLayout isSignUp={false} />}>
        <Route path="/reset-password" element={<ResetPasswordPage />} />
      </Route>
      <Route element={<SearchLayout />}>
        <Route path="/recent" element={<RecentSearchPage />} />
        <Route path="/search-result" element={<SearchResultPage />} />
      </Route>
      {/* <Route path="/films" element={<MyFilmsPage />} /> */}
      <Route element={<HeaderLayout />}>
        <Route path="/write-list" element={<MyFilmsPage />} />
        <Route path="/user-list" element={<UserListPage />} />
      </Route>
    </Routes>
  );
}

export default App;
