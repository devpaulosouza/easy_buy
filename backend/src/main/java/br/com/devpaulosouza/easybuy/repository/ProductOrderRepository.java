package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
