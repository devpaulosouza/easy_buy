import React, { useState, useEffect, useContext } from 'react';
import { orderApi } from '../../api/orderApi';
import { getUserId } from '../../util/web';
import ReactPaginate from 'react-paginate';
import { GlobalContext } from '../../context/globalContext';
import { showOrderModal } from './orderModal';

const OrderList = () => {

  const [orders, setOrders] = useState([]);
  const [pageCount, setPageCount] = useState(0);

  const [orderId, setOrderId] = useContext(GlobalContext).orderId;


  const fetchOrders = async ({ userId, page = 0 }) => {
    try {
      const response = await orderApi.getAll({ userId, page });
      setOrders(response.data.content);
      setPageCount(response.data.totalPages);
    } catch (err) {
      console.error(err);
    }
  }

  const handlePageClick = (event) => {
    fetchOrders({ page: event.selected });
  }

  useEffect(() => {
    const userId = getUserId();

    fetchOrders(userId);
  }, []);

  const handleItemClick = (id) => {
    setOrderId(id);
    showOrderModal(true);
  }


  return ([
    <>
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
              return <tr style={{ cursor: 'pointer' }} key={id} onClick={() => handleItemClick(id)}>
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
    </>
  ]);
}

export default OrderList;
