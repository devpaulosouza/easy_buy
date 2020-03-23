import React, { useEffect, useState } from 'react';
import { withRouter } from 'react-router-dom';
import { getUserId } from '../../util/web';
import { orderApi } from '../../api/orderApi';
import ReactPaginate from 'react-paginate';

const Order = ({ history }) => {

  const [ orders, setOrders ] = useState([]);
  const [ pageCount, setPageCount ] = useState(0);


  const fetchOrders = async (userId) => {
    try {
      const response = await orderApi.getAll({userId, page: 0});
      setOrders(response.data.content);
      setPageCount(response.data.totalPages);
    } catch(err) {
      console.error(err);
    }
      
  }


  const handlePageClick = (event) => {
    fetchOrders(event.selected);
  }


  useEffect(() => {
    const userId = getUserId();

    fetchOrders(userId);
  }, []);

  

  return (
    <>
    <h3>pedidos</h3>
    <div>
      <table className="table table-hover table-product">
        <thead>
          <tr>
            <th scope="col">Número</th>
            <th scope="col">Cliente</th>
            <th scope="col">Total</th>
          </tr>
        </thead>
        <tbody>
          {
            orders.map((order) => {
              const { number, id, total, userName } = order
              return <tr key={id}>
                <td>
                  {number}
                </td>                
                <td>
                  {userName}
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
    </>
  )
}


export default withRouter(Order);
