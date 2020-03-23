import React, { useContext } from 'react';
import { GlobalContext } from '../../context/globalContext';

const Cart = () => {

  const [productInCart, setProductInCart] = useContext(GlobalContext).productsInCart;

  const calculateTotal = () => {
    let total = 0

    if (productInCart) {
      total = productInCart.map(p => p.quantity * p.price).reduce((a,b)=> a + b, 0);
    }

    return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(total);
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

  return (
    <div>
      <div className=""><b>Total:</b> <span>{calculateTotal()}</span></div>
      <table className="table">
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
    </div>
  )
}

export default Cart;
