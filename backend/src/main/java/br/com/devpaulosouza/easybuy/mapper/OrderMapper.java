package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.*;
import br.com.devpaulosouza.easybuy.model.Order;
import br.com.devpaulosouza.easybuy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
        ProductOrderMapper.class,
        UserOutputSimplifiedDto.class
})
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "uuid", source = "id"),
            @Mapping(target = "id", ignore = true),
    })
    Order toEntity(OrderInputDto dto);

    @Mappings({
            @Mapping(target = "id", source = "uuid"),
            @Mapping(target = "userId", source = "user.uuid"),
            @Mapping(target = "userName", source = "user.name")
    })
    OrderDetailedDto toDetailedDto(Order order);

    @Mappings({
            @Mapping(target = "id", source = "uuid"),
            @Mapping(target = "userId", source = "user.uuid"),
            @Mapping(target = "userName", source = "user.name")
    })
    OrderSimplifiedDto toSimplifiedDto(Order order);

}
