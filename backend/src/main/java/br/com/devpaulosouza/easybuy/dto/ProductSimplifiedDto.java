package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductSimplifiedDto implements Dto {

    private UUID id;

    private String code;

    private String shortDescription;

    private BigDecimal price;

    private BigDecimal quantity;

}
