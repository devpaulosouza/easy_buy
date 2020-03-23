import React from 'react';

const ProductOrderList = ({ products=[] }) => {    
    return (
      <div>
        <table className="table table-hover table-product">
          <thead>
            <tr>
              <th scope="col">Código</th>
              <th scope="col">Nome</th>
              <th scope="col">Quantidade</th>
              <th scope="col">Preço</th>
              <th scope="col">Total</th>
            </tr>
          </thead>
          <tbody>
            {
              products.map(({ product, quantity, price: total  }) => {
                const { code, id, shortDescription, price } = product
                return <tr key={id}>
                  <td>
                    {code}
                  </td>                
                  <td>
                    {shortDescription}
                  </td>          
                  <td>
                    {quantity}
                  </td>
                  <td>
                    {Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(price)}
                  </td>
                  <td>
                    {Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(total)}
                  </td>
                </tr>
              }
              )
            }
          </tbody>
        </table>
      </div>
    )
  }

  export default ProductOrderList;
