package br.com.devpaulosouza.easybuy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@ToString
public class ProductOrder extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "productId", updatable = false, nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Column(nullable = false)
    private BigDecimal price;

}
