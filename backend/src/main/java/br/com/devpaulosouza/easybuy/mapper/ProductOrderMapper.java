package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.ProductOrderDto;
import br.com.devpaulosouza.easybuy.model.ProductOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface ProductOrderMapper {

    @Mappings({
            @Mapping(target = "id", source = "uuid"),
            @Mapping(target = "productId", source = "product.uuid"),
            @Mapping(target = "orderId", source = "order.uuid"),
    })
    ProductOrderDto toDto(ProductOrder order);

    @Mappings({
            @Mapping(target = "uuid", source = "id"),
            @Mapping(target = "product.uuid", source = "productId"),
            @Mapping(target = "order.uuid", source = "orderId"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "product.id", ignore = true),
            @Mapping(target = "order.id", ignore = true),
    })
    ProductOrder toEntity(ProductOrderDto dto);

}
