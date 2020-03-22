import React, { useState, useEffect } from 'react';
import { productApi } from '../../api/product';
import ReactPaginate from 'react-paginate';

const ProductList = ({
  onAdd,
  onRemove
}) => {

  const [products, setProducts] = useState([]);
  const [pageCount, setPageCount] = useState(0);

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

  const handlePageClick = (event) => {
    fetchProducts(event.selected);
  }

  return (
    <div>
      <table className="table table-product">
        <thead>
          <tr>
            <th scope="col">Nome</th>
            <th scope="col">Preço</th>
            <th scope="col">Ações</th>
          </tr>
        </thead>
        <tbody>
          {
            products.map(({ id, shortDescription, price }) => (
              <tr key={id}>
                <td>
                  {shortDescription}
                </td>
                <td>
                  {Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(price)}
                </td>
                <td>
                  <button
                    className="btn btn-success"
                    onClick={onAdd}
                  >
                    Adicionar
                  </button>
                </td>
              </tr>
            ))
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
