export interface ISelectBox {
  text: string;
  id: number;
  onClick: () => void;
  isBold: boolean;
}

export interface ISortBottomSheet {
  onClose: () => void;
}
