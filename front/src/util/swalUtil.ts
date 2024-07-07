import { SweetAlertOptions } from 'sweetalert2';

const setSwalText = (swalOption: SweetAlertOptions, text: string) => {
  swalOption.text = text;
  return swalOption;
};

export default setSwalText;
