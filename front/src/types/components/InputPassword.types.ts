export interface IInputPassword {
  value?: string;
  setValue: (value: string) => void;
}

export interface ICheckArea {
  isUpperCase: boolean;
  isLowerCase: boolean;
  isSpecialCharacter: boolean;
}
