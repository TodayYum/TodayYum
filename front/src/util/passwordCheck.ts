export const isUpperCase = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/
  const upperRegex = /^(?=.*[A-Z]).{1,}$/;

  return upperRegex.test(password);
};

export const isSpecialCharacter = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/

  const speciatlChacracterRegex = /^(?=.*[!@#$%^&*?_]).{1,}$/;
  return speciatlChacracterRegex.test(password);
};

export const isLowerCase = (password: string | undefined) => {
  if (password === undefined) return false;
  // 정규표현식 /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/

  const lowerRegex = /^(?=.*[a-z]).{1,}$/;
  return lowerRegex.test(password);
};

export const isRightPassword = (password: string | undefined) => {
  return isLowerCase(password);
  // return (
  //   isUpperCase(password) &&
  //   isLowerCase(password) &&
  //   isSpecialCharacter(password)
  // );
};
