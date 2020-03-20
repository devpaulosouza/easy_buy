package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            "SELECT ( COALESCE(MAX(o.number), 0) + 1 ) " +
            "   FROM Order o "
    )
    Long getNextNumber();

}
