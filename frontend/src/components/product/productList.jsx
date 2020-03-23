import React, { useState, useEffect, useContext, memo } from 'react';
import { productApi } from '../../api/product';
import ReactPaginate from 'react-paginate';
import { GlobalContext } from '../../context/globalContext';

const ProductList = () => {

  const [products, setProducts] = useState([]);
  const [pageCount, setPageCount] = useState(0);


  const [ productInCart, setProductInCart ] = useContext(GlobalContext).productsInCart;

  const fetchProducts = async (page) => {

    try {
      const response = await productApi.getAll(page);
      if (response.status === 200) {
        console.log(response.data.content);
        setPageCount(response.data.totalPages);
        setProducts(response.data.content);
      }
    } catch (err) {
      console.error(err);
    }

  }

  useEffect(() => {
    fetchProducts(0);
  }, [])


  const handleAdd = (product) => {
    const { id: productId } = product

    const p = productInCart.filter(p => p.id === productId);

    if (p.length) {
      const pc = productInCart.map(p =>{
        if (p.id === productId) {
          return {
            ...p,
            quantity: p.quantity + 1
          }
        } else {
          return p;
        }
      });

      setProductInCart(pc);
    } else {
      setProductInCart([...productInCart, { ...product, quantity: 1 }]);
    }
  }

  const getQuantityInCart = (productId) => {
    if (productInCart) {
      const [ ps ] = productInCart.filter(p => p.id === productId);

      return ps ? ps.quantity : 0;
    }
    return 0;
  }

  return (
    <div>
      <table className="table table-hover table-product">
        <thead>
          <tr>
            <th scope="col">Código</th>
            <th scope="col">Nome</th>
            <th scope="col">Preço</th>
            <th scope="col">Ações</th>
          </tr>
        </thead>
        <tbody>
          {
            products.map((product) => {
              const { code, id, shortDescription, price, quantity } = product
              return <tr key={id}>
                <td>
                  {code}
                </td>                
                <td>
                  {shortDescription}
                </td>
                <td>
                  {Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(price)}
                </td>
                <td>
                  <button
                    className="btn btn-success"
                    onClick={() => handleAdd(product)}
                    disabled={quantity <= getQuantityInCart(id)}
                  >
                    Adicionar
                  </button>
                </td>
              </tr>
            }
            )
          }
        </tbody>
      </table>

      <ReactPaginate
        previousLabel={'Anterior'}
        nextLabel={'Próximo'}
        breakLabel={<div>...</div>}
        reakClassName="break-me"
        pageCount={pageCount}
        marginPagesDisplayed={2}
        pageRangeDisplayed={4}
        onPageChange={handlePageClick}
        containerClassName="pagination d-flex justify-content-end"
        subContainerClassName="pages pagination"
        activeClassName="active"
        initialPage={0}
        pageClassName="page-item"
        pageLinkClassName="page-link"
        nextClassName="page-item"
        nextLinkClassName="page-link"
        previousClassName="page-item"
        previousLinkClassName="page-link"
      />
    </div>
  )
}



export default ProductList;


