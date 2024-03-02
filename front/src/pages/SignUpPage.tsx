import { useEffect } from 'react';
import RegistEmail from '../organisms/RegistEmail';
import { useSignInDataAtom, useInitSignInDataAtom } from '../jotai/signInData';
import RegistPassword from '../organisms/RegistPassword';
import RegistNickname from '../organisms/RegistNickname';

function SignUpPage() {
  const registData = useSignInDataAtom();
  const setInitSignInData = useInitSignInDataAtom();

  useEffect(() => {
    setInitSignInData();
  }, []);

  return (
    <div className="bg-background">
      {registData.signUpLevel < 2 && <RegistEmail isSignUp />}
      {registData.signUpLevel === 2 && <RegistPassword isSignUp />}
      {registData.signUpLevel === 3 && <RegistNickname />}
    </div>
  );
}

export default SignUpPage;
