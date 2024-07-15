import "./App.css";
import { Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";
import ResetPasswordPage from "./pages/ResetPasswordPage";
import HeaderOnlyLayout from "./layout/HeaderOnlyLayout";
import MainPage from "./pages/MainPage";
import NavBarLayout from "./layout/NavBarLayout";
import SearchLayout from "./layout/SearchLayout";
import RecentSearchPage from "./pages/RecentSearchPage";
import SearchResultPage from "./pages/SearchResultPage";
import FilmDetailPage from "./pages/FilmDetailPage";
import UploadFicturesPage from "./pages/UploadFicturesPage";
import CreatePolaroidFilmPage from "./pages/CreatePolaroidFilmPage";
import UserProfilePage from "./pages/UserProfilePage";
import HeaderLayout from "./layout/HeaderLayout";
import MyFilmsPage from "./pages/MyFilmsPage";
import UserListPage from "./pages/UserListPage";
import EditDetailPage from "./pages/EditDetailPage";
import CheckAuthRoute from "./organisms/CheckAuthRoute";

function App() {
  return (
    <Routes>
      {/* <Route element={<NavBarLayout />}>
        <Route path="/" element={<MainPage />} />
        <Route path="/board/:boardId" element={<FilmDetailPage />} />
        <Route path="/edit-film" element={<EditDetailPage />} />
        <Route path="/profile" element={<UserProfilePage />} />
      </Route> */}
      <Route path="/login" element={<LoginPage />} />
      <Route element={<HeaderOnlyLayout isSignUp />}>
        <Route path="/sign-up" element={<SignUpPage />} />
      </Route>
      <Route element={<HeaderOnlyLayout isSignUp={false} />}>
        <Route path="/reset-password" element={<ResetPasswordPage />} />
      </Route>
      {/* 토큰 확인이 필요한 페이지들 */}
      <Route element={<CheckAuthRoute />}>
        <Route element={<NavBarLayout />}>
          <Route path="/" element={<MainPage />} />
          <Route path="/board/:boardId" element={<FilmDetailPage />} />
          <Route path="/edit-film" element={<EditDetailPage />} />
          <Route path="/profile" element={<UserProfilePage />} />
        </Route>
        <Route path="/create-board" element={<NavBarLayout />}>
          <Route path="select-fictures" element={<UploadFicturesPage />} />
          <Route path="" element={<CreatePolaroidFilmPage />} />
        </Route>
        <Route element={<SearchLayout />}>
          <Route path="/recent" element={<RecentSearchPage />} />
          <Route path="/search-result" element={<SearchResultPage />} />
        </Route>
        <Route element={<HeaderLayout />}>
          <Route path="/write-list" element={<MyFilmsPage />} />
          <Route path="/user-list" element={<UserListPage />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;
