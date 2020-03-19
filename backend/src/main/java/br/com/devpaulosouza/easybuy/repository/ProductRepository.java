package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByUuid(UUID uuid);

}
