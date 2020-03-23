import React, { useEffect, useState, useContext } from 'react';
import { withRouter } from 'react-router-dom';
import { OrderList } from '../../components';
import OrderModal from '../../components/order/orderModal';
import { isAuthenticated } from '../../util/web';

const Order = ({ history }) => {
  if (!isAuthenticated()) {
    history.push('/product');
  }


  return (
    <div className="container">
      <h2>Pedidos</h2>
      <OrderList />
      <OrderModal />
    </div>
  )
}


export default withRouter(Order);
