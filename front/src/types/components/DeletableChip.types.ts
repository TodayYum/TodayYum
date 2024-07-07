export interface IDeletableChip {
  text: string;
  deleteSearchWord: () => void;
  onSelectClick: () => void;
  dataId: string;
}
