import React, { useContext, useEffect, useState } from 'react';
import $ from 'jquery';
import { GlobalContext } from '../../context/globalContext';
import ProductOrderList from './productOrderList';
import { orderApi } from '../../api/orderApi';

export const showOrderModal = (visible) => {
  if (visible) {
    $('#order-modal').modal({
      show: visible
    });
  } else {
    $(`#order-modal`).modal('hide');
  }
}

const OrderModal = () => {

  const [orderId,] = useContext(GlobalContext).orderId;

  const [order,setOrder] = useState({});

  const fetchOrder = async (id) => {
    try {
      const response = await orderApi.get(id);

      console.log(response.data);
      setOrder(response.data);

    } catch(err) {
      console.error(err);
    }
  }

  useEffect(() => {
    fetchOrder(orderId);
  }, [orderId]);


  return (
    <div className="modal fade" id="order-modal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content" style={{width: '600px'}}>
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">Pedido</h5>
            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div className="modal-body">
            <p><b>Número:</b> <span>{order && order.number}</span></p>
            <p><b>Cliente:</b> <span>{order && order.userName}</span></p>
            <p><b>Total:</b> {Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(order && order.total)}</p>
            <ProductOrderList products={order && order.products}/>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-outline-danger" data-dismiss="modal">Fechar</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default OrderModal;
