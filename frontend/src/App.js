import React, { useState } from 'react';

import 'sweetalert2/src/sweetalert2.scss'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap';
import './app.css'

import { Routes } from './routes';
import { GlobalContext } from './context/globalContext';



const App = () => (
  <GlobalContext.Provider
    value={{
      productsInCart: useState([]),
      authenticated: useState(false),
      orderId: useState()
    }}
    >
    <Routes />
  </GlobalContext.Provider>
)

export default App;
