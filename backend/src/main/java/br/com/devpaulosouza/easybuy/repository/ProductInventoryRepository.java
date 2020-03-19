package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    @Query(
            value =
            "SELECT pi.* " +
            "   FROM product_inventory pi \n" +
            "   INNER JOIN product p ON p.id = pi.product_id \n" +
            "   WHERE p.uuid = :uuidProduct \n" +
            "   ORDER BY pi.created_at DESC \n" +
            "   LIMIT 1",
            nativeQuery = true
    )
    ProductInventory findLastProductInventoryByUuidProduct(@Param("uuidProduct") UUID uuidProduct);

}
