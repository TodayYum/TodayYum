import { useEffect } from 'react';
import RegistEmail from '../organisms/RegistEmail';
import { useSignUpDataAtom, useInitsignUpDataAtom } from '../jotai/signUpData';
import RegistPassword from '../organisms/RegistPassword';

function ResetPasswordPage() {
  const registData = useSignUpDataAtom();
  const setInitRegistData = useInitsignUpDataAtom();

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
