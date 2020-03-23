import React, { useContext, useEffect, useState } from 'react';

import $ from 'jquery';
import { GlobalContext } from '../../context/globalContext';
import { productApi } from '../../api/product';

export const showProductModal = (visible) => {
  if (visible) {
    $('#order-modal').modal({
      show: visible
    });
  } else {
    $(`#order-modal`).modal('hide');
  }
}


const ModalProductDetailed = () => {

  const [product, setProduct] = useState({});
  const [productId,] = useContext(GlobalContext).productId;

  const fetchProduct = async (productId) => {
    try {
      const response = await productApi.get(productId);
      console.log(response)
      setProduct(response.data);
    } catch(err) {
      console.error(err);
    }

  }

  useEffect(() => {
    fetchProduct(productId);
  }, [productId]);


  const { name, code, shortDescription, description, quantity, price } = product || {};

  return (
    <div className="modal fade" id="order-modal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content" style={{ width: '600px' }}>
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">Detalhes do produto</h5>
            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div className="modal-body">
            <div>
              <p>Nome: <span>{name}</span></p>
              <p>Código: <span>{code}</span></p>
              <p>Descrição: <span>{shortDescription}</span></p>
              <p>Detalhes: <span>{description}</span></p>
              <p>Quantidade em estoque: <span>{quantity}</span></p>
              <p>Preço: <span>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(price)}</span></p>
            </div>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-outline-danger" data-dismiss="modal">Fechar</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ModalProductDetailed;
