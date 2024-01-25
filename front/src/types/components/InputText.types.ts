export interface IInputText {
  type: string;
  placeholder: string;
  successText: string;
  failText: string;
  value?: string;
  isSuccess: number;
  disabled?: boolean;
  setValue: (value: string) => void;
}

export interface ISupoortText {
  isSuccess: number;
  successText: string;
  failText: string;
}
