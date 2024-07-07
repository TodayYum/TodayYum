import { SweetAlertOptions } from 'sweetalert2';

export const swalFail: SweetAlertOptions = {
  icon: 'warning',
  text: '',
  width: '300px',
  confirmButtonText: '돌아가기',
  confirmButtonColor: '#787D85',
};

export const swalSuccess: SweetAlertOptions = {
  icon: 'success',
  text: '',
  width: '300px',
  confirmButtonText: '돌아가기',
  confirmButtonColor: '#787D85',
};

export const swalNavigateLogin: SweetAlertOptions = {
  text: '로그인이 필요합니다.',
  width: '300px',
  confirmButtonText: '이동하기',
  confirmButtonColor: '#787D85',
};
