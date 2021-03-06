package br.com.devpaulosouza.easybuy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@ToString
public class Product extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String description;

    @Column(length = 60)
    private String shortDescription;

    @Column(nullable = false)
    private BigDecimal price;

    @Formula(
            "(SELECT pi.quantity from product_inventory pi where pi.product_id = id order by pi.created_at desc limit 1)"
    )
    @Basic
    private BigDecimal quantity;

}
