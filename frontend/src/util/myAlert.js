import Swal from 'sweetalert2/dist/sweetalert2.js';


// import Auth from '../components/auth/authModal';


export const myAlert = {
  confirm: (title = 'Confirmar', text = 'Deseja confirmar a ação?', onSuccess) => {
    Swal.fire({
      title: title,
      text: text,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sim',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        onSuccess && onSuccess()
      }
    })
  }
}
