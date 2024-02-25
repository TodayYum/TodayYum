import Header from '../atoms/Header';
import RegistEmail from '../organisms/RegistEmail';
import { useRegistDataAtom } from '../jotai/signUpAtom';
import RegistPassword from '../organisms/RegistPassword';

function SignUpPage() {
  const registData = useRegistDataAtom();

  return (
    <div className="bg-background">
      <Header title="회원가입" />
      {registData.signUpLevel < 2 ? <RegistEmail /> : <RegistPassword />}
    </div>
  );
}

export default SignUpPage;
