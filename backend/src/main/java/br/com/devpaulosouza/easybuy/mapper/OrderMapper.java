package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.OrderDto;
import br.com.devpaulosouza.easybuy.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { ProductOrderMapper.class })
public interface OrderMapper {
    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    OrderDto toDto(Order order);

    @Mappings({
            @Mapping(target = "uuid", source = "id"),
            @Mapping(target = "id", ignore = true),
    })
    Order toEntity(OrderDto dto);

}
