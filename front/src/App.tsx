import './App.css';
import { Route, Routes } from 'react-router-dom';
import Test from './Test';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import ResetPasswordPage from './pages/ResetPasswordPage';
import HeaderOnlyLayout from './layout/HeaderOnlyLayout';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Test />} />
      <Route path="/login" element={<LoginPage />} />
      <Route element={<HeaderOnlyLayout isSignUp />}>
        <Route path="/sign-up" element={<SignUpPage />} />
      </Route>
      <Route element={<HeaderOnlyLayout isSignUp={false} />}>
        <Route path="/reset-password" element={<ResetPasswordPage />} />
      </Route>
    </Routes>
  );
}

export default App;
