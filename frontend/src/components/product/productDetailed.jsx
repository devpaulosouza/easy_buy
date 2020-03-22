import React from 'react';

const Product = ({ name, description, shorDescription, price }) => {

  return (
    <div>
      <p>Nome: <span>{name}</span></p>
      <p>Descrição: <span>{shorDescription}</span></p>
      <p>Detalhes: <span>{description}</span></p>
      <p>Preço: <span>{new Intl().NumberFormat('pt-BR', { style: 'currency', currency: 'JPY' }).format(price)}</span></p>
    </div>
  )
}

export default Product;
