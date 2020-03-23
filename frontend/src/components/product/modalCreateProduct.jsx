import React, { useState, useContext } from 'react';
import $ from 'jquery';
import { productApi } from '../../api/product';
import { GlobalContext } from '../../context/globalContext';

export const showCreateProductModal = (visible) => {
  if (visible) {
    $('#create-product-modal').modal({
      show: visible
    });
  } else {
    $(`#create-product-modal`).modal('hide');
  }
}

const CreateProductModal = () => {

  const [shortDescription, setShortDescription] = useState('');
  const [description, setDescription] = useState('');
  const [quantity, setQuantity] = useState('');
  const [code, setCode] = useState(parseInt(Math.random() * 1000));
  const [price, setPrice] = useState();

  const isValidShortDescription = shortDescription.length > 5;
  const isValidDescription = description.length > 5;
  const isValidPrice = !isNaN(price);
  const isValidCode = !!code;
  const isValidQuanitty = quantity && !isNaN(quantity);

  const [ , setRefresh ] = useContext(GlobalContext).refresh;



  const clear = () => {
    setShortDescription('');
    setDescription('');
    setCode(parseInt(Math.random() * 1000));
    setPrice('');
    setQuantity('');
  }

  const handleSubmit = () => {

    if (isValidPrice && isValidDescription && isValidShortDescription && isValidCode) {
      try {
        productApi.post({ code, description, shortDescription, price, quantity });
        clear();
        showCreateProductModal(false);
        setRefresh(true);
      } catch(err) {
        console.error(err);
      }
    }
  }

  const handleCancel = () => {
    clear();
  }

  return (
    <div className="modal fade" id="create-product-modal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content" style={{ width: '600px' }}>
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">Criar produto</h5>
            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div className="modal-body">

            <div className="form-group">
              <label htmlFor="desc">Nome: </label>
              <input type="text" value={shortDescription} onChange={({ target: { value } }) => { setShortDescription(value) }} className={`form-control ${!isValidShortDescription ? 'is-invalid' : ''}`} id="desc" />
            </div>
            <div className="form-group">
              <label htmlFor="short-desc">Descrição: </label>
              <input type="text" value={description} onChange={({ target: { value } }) => { setDescription(value) }} className={`form-control ${!isValidDescription ? 'is-invalid' : ''}`} id="short-desc" />
            </div>
            <div className="form-group">
              <label htmlFor="code">Código: </label>
              <input type="text" value={code} onChange={({ target: { value } }) => { setCode(value) }} className={`form-control ${!code ? 'is-invalid' : ''}`} id="code" />
            </div>
            <div className="form-group">
              <label htmlFor="quantity">Quantidade: </label>
              <input type="text" value={quantity} onChange={({ target: { value } }) => { setQuantity(value) }} className={`form-control ${!isValidQuanitty ? 'is-invalid' : ''}`} id="quantity" />
            </div>
            <div className="form-group">
              <label htmlFor="price">Preço: </label>
              <input type="text" value={price} onChange={({ target: { value } }) => { setPrice(value) }} className={`form-control ${!isValidPrice ? 'is-invalid' : ''}`} id="price" />
            </div>
          </div>
          <div className="modal-footer">
            <button type="button" onClick={handleCancel} className="btn btn-outline-danger" data-dismiss="modal">Fechar</button>
            <button type="button" onClick={handleSubmit} className="btn btn-success">Criar</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CreateProductModal;
