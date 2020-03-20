package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.ProductInventoryInputDto;
import br.com.devpaulosouza.easybuy.dto.ProductInventoryOutputDto;
import br.com.devpaulosouza.easybuy.model.ProductInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper {

    @Mappings({
            @Mapping(target = "id", source = "uuid"),
            @Mapping(target = "productId", source = "product.uuid")
    })
    ProductInventoryOutputDto toDto(ProductInventory product);

    @Mappings({
            @Mapping(target = "product.uuid", source = "productId"),
    })
    ProductInventory toEntity(ProductInventoryInputDto dto);

}
