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

const SECOND = 1;
const MINUTE = SECOND * 60;
const HOUR = MINUTE * 60;
const DAY = HOUR * 24;

export const getTimeBefore = (writeDate: string) => {
  const writeTime = new Date(writeDate);
  const present = new Date();
  const passedSeconds = Math.trunc(
    (present.getTime() - writeTime.getTime() - 9000 * HOUR) / 1000 + HOUR * 9,
  );
  if (passedSeconds < SECOND) {
    return '방금 전';
  }
  if (passedSeconds < MINUTE) {
    return `${passedSeconds}초 전`;
  }
  if (passedSeconds < HOUR) {
    return `${Math.trunc(passedSeconds / MINUTE)}분 전`;
  }
  if (passedSeconds < DAY) {
    return `${Math.trunc(passedSeconds / HOUR)}시간 전`;
  }
  if (passedSeconds < DAY * 8) {
    return `${Math.trunc(passedSeconds / DAY)}일 전`;
  }
  return writeTime.toLocaleDateString();
};
