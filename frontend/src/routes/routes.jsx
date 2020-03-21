import React from 'react';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";



function Produtos() {
  return <h2>Produtos</h2>;
}

function Pedidos() {
  return <h2>Pedidos</h2>;
}

function Login() {
  return <h2>Login</h2>;
}

const Routes = () => (
  <Router>
    <div>
      <nav>
        <ul>
          <li>
            <Link to="/">Produtos</Link>
          </li>
          <li>
            <Link to="/Pedidos">Pedidos</Link>
          </li>
          <li>
            <Link to="/Login">Login</Link>
          </li>
        </ul>
      </nav>

      {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
      <Switch>
        <Route path="/pedidos">
          <Pedidos />
        </Route>
        <Route path="/auth">
          <Login />
        </Route>
        <Route path="/produtos">
          <Produtos />
        </Route>

        <Route exact="/" />
      </Switch>
    </div>
  </Router>
);

export default Routes;

