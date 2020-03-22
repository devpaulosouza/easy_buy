import React, { useState, useEffect } from 'react';
import { ProductList, Cart } from '../../components';

const Product = () => {
  return (
    <div className="container">
      <h2>Produtos</h2>
      <ProductList />

      <h2>Carrinho</h2>
      <Cart />
    </div>
  )
}

export default Product;
