import './App.css';
import { Route, Routes } from 'react-router-dom';
import Test from './Test';
import LoginPage from './pages/LoginPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Test />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
}

export default App;
