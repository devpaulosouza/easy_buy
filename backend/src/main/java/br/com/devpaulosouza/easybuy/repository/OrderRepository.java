package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            "SELECT ( COALESCE(MAX(o.number), 0) + 1 ) " +
            "   FROM Order o "
    )
    Long getNextNumber();

    @Query(
            "SELECT o FROM Order o" +
            "   JOIN FETCH o.products " +
            "   JOIN o.user u " +
            "   WHERE o.uuid = :uuid " +
            "       AND u.id = :userId "
    )
    Order findByUuidAndUserId(@Param("uuid") UUID uuid, @Param("userId") Long userId);

    @Query(
            "SELECT o FROM Order o" +
            "   JOIN FETCH o.products " +
            "   WHERE o.uuid = :uuid "
    )
    Order findByUuid(UUID uuid);

    Page<Order> findAllByUserUuid(@Param("uuid") UUID userId, Pageable pageable);

}
