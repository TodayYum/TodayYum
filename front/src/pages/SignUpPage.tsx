import { useEffect } from 'react';
import RegistEmail from '../organisms/RegistEmail';
import { useSignUpDataAtom, useInitsignUpDataAtom } from '../jotai/signUpData';
import RegistPassword from '../organisms/RegistPassword';
import RegistNickname from '../organisms/RegistNickname';

function SignUpPage() {
  const registData = useSignUpDataAtom();
  const setInitsignUpData = useInitsignUpDataAtom();

  useEffect(() => {
    setInitsignUpData();
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
