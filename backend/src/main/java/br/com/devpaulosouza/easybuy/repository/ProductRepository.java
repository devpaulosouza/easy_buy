package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByUuid(UUID uuid);

    @Query(
            "SELECT p " +
            "   FROM Product p " +
            "   WHERE p.uuid in :uuids"
    )
    List<Product> findAllByUuid(@Param("uuids") List<UUID> uuids);

}
