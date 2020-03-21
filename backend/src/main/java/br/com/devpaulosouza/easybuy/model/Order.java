package br.com.devpaulosouza.easybuy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_")
@Getter
@Setter
@ToString
public class Order extends AbstractEntity {

    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long number;

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Formula(
            "(SELECT SUM(po.price) " +
            "   FROM product_order po " +
            "   INNER JOIN order_ o ON o.id = po.order_id " +
            "WHERE o.id = id)"
    )
    @Basic
    private BigDecimal total;

}
