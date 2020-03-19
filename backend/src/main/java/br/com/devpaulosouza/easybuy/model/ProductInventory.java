package br.com.devpaulosouza.easybuy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString
public class ProductInventory implements br.com.devpaulosouza.easybuy.model.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.getUuid())) {
            this.setUuid(UUID.randomUUID());
        }
    }

    @JoinColumn(name = "product_id", updatable = false)
    @OneToOne
    Product product;

    @Column(nullable = false, updatable = false)
    BigDecimal quantity;

    @Column(updatable = false)
    BigDecimal previousQuantity;

    // algum dia (ou nunca) encontro um nome melhor pra isso
    @Column(nullable = false, updatable = false)
    BigDecimal quantityOffset;

}
