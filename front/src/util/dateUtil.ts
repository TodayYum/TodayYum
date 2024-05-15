export const getDate = (idx: number) => {
  const currentDate = new Date();
  const date = new Date();
  date.setDate(currentDate.getDate() - idx);
  return date.toLocaleDateString();
};

export const ISOtoLocal = (isoString: string) => {
  const date = new Date(isoString);
  const localString = date.toLocaleDateString();
  return localString.substring(5, localString.length - 1);
};
