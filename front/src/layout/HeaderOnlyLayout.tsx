import { Outlet } from 'react-router-dom';
import Header from '../atoms/Header';

function HeaderOnlyLayout({ isSignUp }: { isSignUp: boolean }) {
  return (
    <div>
      <Header title={`${isSignUp ? '회원가입' : '비밀번호 찾기'}`} />
      <Outlet />
    </div>
  );
}

export default HeaderOnlyLayout;
