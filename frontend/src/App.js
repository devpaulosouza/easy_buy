import React, { useState } from 'react';

import 'bootstrap/dist/css/bootstrap.min.css';
import './app.css'

import { Routes } from './routes';
import { GlobalContext } from './context/globalContext';

const App = () => (
  <GlobalContext.Provider
    value={{
      productsInCart: useState([])
    }}
    >
    <Routes />
  </GlobalContext.Provider>
)

export default App;
