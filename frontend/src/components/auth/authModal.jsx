import React, { useState, useContext } from 'react';
import { authApi } from '../../api/auth';
import $ from 'jquery';
import { GlobalContext } from '../../context/globalContext';

const validateEmail = (email = '') => email.length >= 5;
const validatePassword = (email = '') => email.length >= 5;


export const showAuthModal = (visible) => {
  console.log('modal visible ' + visible);

  console.log($('#auth-modal'));

  if (visible) {

    $('#auth-modal').modal({
      show: visible
    });
  } else {
    $(`#auth-modal`).modal('hide');
  }
}

const AuthModal = () => {

  const [, setAuthenticated] = useContext(GlobalContext).authenticated;


  const [register, setRegister] = useState(false);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = async () => {
    const isValid = register ?
      validateEmail(username) && validatePassword(password) && password === confirmPassword
      : validateEmail(username) && validatePassword(password);

    if (isValid) {
      try {
        const res = await authApi.post(username, password);
        window.localStorage.setItem('token', res.data.token);
        window.localStorage.setItem('role', res.data.role);
        showAuthModal(false);
        setAuthenticated(true);
      } catch (err) {
        console.error(err);
      }
    }

  }


  // namoral, são 23:14. Não vou fazer este código ficar bonitinho não. Vai desse jeito porco mesmo.

  const emailValid = validateEmail(username);
  const passwordValid = validatePassword(password);
  const confirmPpasswordValid = password === confirmPassword;


  return (
    <div className="modal fade" id="auth-modal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">Login</h5>
            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div className="modal-body">
            {
              register ? (
                <form noValidate>
                  <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" value={username} onChange={({ target: { value } }) => { setUsername(value) }} className={`form-control ${!emailValid ? 'is-invalid' : ''}`} id="email" />
                  </div>
                  <div className="form-group">
                    <label htmlFor="password">Senha</label>
                    <input type="password" onChange={({ target: { value } }) => { setPassword(value) }} className={`form-control ${!passwordValid ? 'is-invalid' : ''}`} id="password" />
                  </div>
                  <div className="form-group">
                    <label htmlFor="confirm-password">Confirmar senha</label>
                    <input type="password" onChange={({ target: { value } }) => { setConfirmPassword(value) }} className={`form-control ${!confirmPpasswordValid ? 'is-invalid' : ''}`} id="confirm-password" />
                  </div>
                  <p>Já possui uma conta? <span className="btn-link" onClick={() => setRegister(false)}> entrar </span></p>
                </form>
              ) : (
                  <form noValidate>
                    <div className="form-group">
                      <label htmlFor="email">Email</label>
                      <input type="email" value={username} onChange={({ target: { value } }) => { setUsername(value) }} className={`form-control ${!emailValid ? 'is-invalid' : ''}`} id="email" />
                    </div>
                    <div className="form-group">
                      <label htmlFor="password">Senha</label>
                      <input type="password" onChange={({ target: { value } }) => { setPassword(value) }} className={`form-control ${!passwordValid ? 'is-invalid' : ''}`} id="password" />
                    </div>
                    <p>Não tem uma conta? <span className="btn-link" onClick={() => setRegister(true)}> cadastrar </span></p>
                  </form>
                )
            }
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-outline-danger" data-dismiss="modal">Cancelar</button>
            <button type="button" className="btn btn-primary" onClick={handleSubmit}>Login</button>
          </div>
        </div>
      </div>
    </div>
  )


}

export default AuthModal;