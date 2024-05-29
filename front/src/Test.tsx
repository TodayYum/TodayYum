import { useNavigate } from 'react-router-dom';
// import Header from './atoms/Header';
import Navbar from './atoms/Navbar';
import RectangleButton from './atoms/RectangleButton';
import RegistNickname from './organisms/RegistNickname';

function Test() {
  const navigate = useNavigate();
  return (
    <div>
      {/* <Header title="테스트입니다" ateAt="01.01" /> */}
      <Navbar />
      <RectangleButton
        text="이건 테스트"
        isCancle
        onClick={() => navigate('login')}
      />
      <RegistNickname />
    </div>
  );
}

export default Test;
