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
public class ProductInventory extends AbstractEntity {

    @JoinColumn(name = "product_id")
    @OneToOne
    Product product;

    @Column(nullable = false)
    BigDecimal quantity;

    @Column
    BigDecimal previousQuantity;

    // algum dia (ou nunca) encontro um nome melhor pra isso
    @Column(nullable = false)
    BigDecimal quantityOffset;

}
