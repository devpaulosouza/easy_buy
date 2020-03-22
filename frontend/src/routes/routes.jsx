import React from 'react';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import { ProductPage, AuthPage, OrderPage } from '../pages';

const Routes = () => (
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
          <li>
            <Link to="/auth">Login</Link>
          </li>
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
          <Redirect to="/product"/>
        </Route>
      </Switch>
    </div>
  </Router>
);

export default Routes;

