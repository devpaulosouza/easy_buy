import React, {  useState, useContext, useEffect } from 'react';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import { ProductPage, AuthPage, OrderPage } from '../pages';
import { AuthModal } from '../components';
import { clearStorage, isAuthenticated } from '../util/web';
import { GlobalContext } from '../context/globalContext';
import { showAuthModal } from '../components/auth/authModal';


const Routes = () => {
  const [authenticated, setAuthenticated] = useContext(GlobalContext).authenticated;

  console.log(authenticated)

  useEffect(() => {
    if (isAuthenticated()) setAuthenticated(true);
  }, []);

  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/product">Produtos</Link>
            </li>
            <li>
              <Link to="/order">Pedidos</Link>
            </li>
            {authenticated ? (
              <li>
                <span className="btn-link" style={{ cursor: 'pointer' }} onClick={() => { clearStorage(); setAuthenticated(false)}}>Logout</span>
              </li>
            ) : (
                <li>
                  <span className="btn-link" onClick={() => { showAuthModal(true) }}>Login</span>
                </li>
              )}

          </ul>
        </nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path="/order">
            <OrderPage />
          </Route>
          <Route path="/auth">
            <AuthPage />
          </Route>
          <Route path="/product">
            <ProductPage />
          </Route>

          <Route exact path="/">
            <Redirect to="/product" />
          </Route>
        </Switch>

        <AuthModal />
      </div>
    </Router>
  )
};

export default Routes;

