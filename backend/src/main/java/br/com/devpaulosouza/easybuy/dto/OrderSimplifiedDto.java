package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderSimplifiedDto {

    private UUID id;

    private BigDecimal number;

    private BigDecimal total;

    private UUID userId;

    private String userName;

}
