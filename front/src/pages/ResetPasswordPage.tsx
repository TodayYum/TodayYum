import { useEffect } from 'react';
import RegistEmail from '../organisms/RegistEmail';
import { useSignInDataAtom, useInitSignInDataAtom } from '../jotai/signInData';
import RegistPassword from '../organisms/RegistPassword';

function ResetPasswordPage() {
  const registData = useSignInDataAtom();
  const setInitRegistData = useInitSignInDataAtom();

  useEffect(() => {
    setInitRegistData();
  }, []);

  return (
    <div className="bg-background">
      {registData.signUpLevel < 2 && <RegistEmail isSignUp={false} />}
      {registData.signUpLevel === 2 && <RegistPassword isSignUp={false} />}
    </div>
  );
}

export default ResetPasswordPage;
