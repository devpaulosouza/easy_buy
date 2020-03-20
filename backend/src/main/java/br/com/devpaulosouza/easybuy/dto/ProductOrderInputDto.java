package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductOrderInputDto {

    private UUID id;

    private UUID orderId;

    @NotNull
    private UUID productId;

    @NotNull
    private BigDecimal quantity;

}
