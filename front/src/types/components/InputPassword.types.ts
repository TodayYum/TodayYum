export interface IInputPassword {
  value?: string;
  setValue: (value: string) => void;
  customClass?: string;
}

export interface ICheckArea {
  isLowerCase: boolean;
  isRightLength: boolean;
  hasNumbers: boolean;
}
