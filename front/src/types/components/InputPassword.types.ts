export interface IInputPassword {
  value?: string;
  setValue: (value: string) => void;
  customClass?: string;
}

export interface ICheckArea {
  isUpperCase: boolean;
  isLowerCase: boolean;
  isSpecialCharacter: boolean;
}
