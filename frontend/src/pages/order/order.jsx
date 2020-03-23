import React, { useEffect, useState } from 'react';
import { withRouter } from 'react-router-dom';
import { OrderList } from '../../components';
import OrderModal from '../../components/order/orderModal';

const Order = ({ history }) => {



  return (
    <div className="container">
      <h2>Pedidos</h2>
      <OrderList />
      <OrderModal />
    </div>
  )
}


export default withRouter(Order);
