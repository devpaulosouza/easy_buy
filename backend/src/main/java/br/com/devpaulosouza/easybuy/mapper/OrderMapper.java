package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.OrderDetailedDto;
import br.com.devpaulosouza.easybuy.dto.OrderInputDto;
import br.com.devpaulosouza.easybuy.dto.OrderOutputDto;
import br.com.devpaulosouza.easybuy.dto.OrderSimplifiedDto;
import br.com.devpaulosouza.easybuy.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { ProductOrderMapper.class })
public interface OrderMapper {
    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    OrderOutputDto toDto(Order order);

    @Mappings({
            @Mapping(target = "uuid", source = "id"),
            @Mapping(target = "id", ignore = true),
    })
    Order toEntity(OrderInputDto dto);

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    OrderDetailedDto toDetailedDto(Order order);

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    OrderSimplifiedDto toSimplifiedDto(Order order);

}
