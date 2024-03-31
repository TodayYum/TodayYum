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

function App() {
  return (
    <Routes>
      <Route element={<NavBarLayout />}>
        <Route path="/" element={<MainPage />} />
        <Route path="/test" element={<FilmDetailPage />} />
        <Route path="/create-board" element={<UploadFicturesPage />} />
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
    </Routes>
  );
}

export default App;
