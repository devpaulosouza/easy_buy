import React, { useContext } from 'react';
import { GlobalContext } from '../../context/globalContext';

import { isAuthenticated, getUserId } from '../../util/web';
import { showAuthModal } from '../auth/authModal';
import { myAlert } from '../../util/myAlert';
import { orderApi } from '../../api/orderApi';
import { withRouter } from 'react-router-dom';

const Cart = ({ history }) => {

  const [productInCart, setProductInCart] = useContext(GlobalContext).productsInCart;

  const calculateTotal = () => {
    let total = 0

    if (productInCart) {
      total = productInCart.map(p => p.quantity * p.price).reduce((a,b)=> a + b, 0);
    }

    return total;
  }

  const handleRemoveOne = (productId) => {

    const ps = productInCart.map(p => {
      if(p.id === productId) {
        return { ...p, quantity: p.quantity -1 }
      } else {
        return p;
      }
    }).filter(p => p.quantity > 0);

    setProductInCart(ps);

  }

  const handleRemoveAll = (productId) => {
    const ps = productInCart.filter(p => p.id !== productId);
    setProductInCart(ps);
  }

  const submit = async () => {
    try {
      const products = productInCart.map(p => ({ productId: p.id, ...p }));

      await orderApi.post({ products });

      setProductInCart([]);

      history.push(`/order`);

    } catch(err) {
      console.error(err);
    }
  }

  const handleFinish = () => {

    if (!isAuthenticated()) {
      showAuthModal(true);
    } else {
      myAlert.confirm('Confirmar compra', 'Deseja confirmar o pedido?', submit)
    }

  }

  const total = calculateTotal();

  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Código</th>
            <th scope="col">Nome</th>
            <th scope="col">Quantidade</th>
            <th scope="col">Total</th>
            <th scope="col">Ações</th>
          </tr>
        </thead>
        <tbody>
          {
            productInCart && productInCart.map(p => (
              <tr key={p.id}>
                <td>{p.code}</td>
                <td>{p.shortDescription}</td>
                <td>{p.quantity}</td>
                <td>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(p.quantity * p.price)}</td>
                <td>
                  <button
                    className="btn btn-outline-danger mr-2"
                    onClick={() => handleRemoveOne(p.id)}
                  >
                    Remover 1
                  </button>
                  <button
                    className="btn btn-outline-danger"
                    onClick={() => handleRemoveAll(p.id)}
                  >
                    Remover todos
                  </button>
                </td>
              </tr>
            ))
          }
        </tbody>
      </table>

      <div className=""><b>Total:</b> <span>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(total)}</span></div>
      <div className="mb-5 float-right">
        <button 
          className="btn btn-success"
          onClick={handleFinish}
          disabled={total === 0}
        >
          Finalizar compra
        </button>
      </div>
    </div>
  )
}

export default withRouter(Cart);
