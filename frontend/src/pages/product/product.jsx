import React, { useState, useEffect } from 'react';
import { ProductList, Cart } from '../../components';
import { getRole } from '../../util/web';
import CreateProductModal, { showCreateProductModal } from '../../components/product/modalCreateProduct';

const Product = () => {
  return (
    <div className="container">
      { getRole() === 'ADMIN'  && 
        <div className="float-right">
          <button 
            className="btn btn-outline-success"
            onClick={() => showCreateProductModal(true)}
            type='button'
          >
            Novo Produto
          </button>
        </div> }
      <h2>Produtos</h2>
      <ProductList />

      <h2>Carrinho</h2>
      <Cart />

      <CreateProductModal />

    </div>
  )
}

export default Product;
