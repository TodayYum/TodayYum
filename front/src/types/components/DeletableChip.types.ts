export interface IDeletableChip {
  text: string;
  onDeleteClick: () => void;
  onSelectClick: () => void;
  dataId: string;
}
