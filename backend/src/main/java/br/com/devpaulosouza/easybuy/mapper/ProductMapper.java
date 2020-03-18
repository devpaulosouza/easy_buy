package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.ProductDto;
import br.com.devpaulosouza.easybuy.dto.ProductSimplifiedDto;
import br.com.devpaulosouza.easybuy.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    ProductDto toDto(Product product);

    List<ProductDto> toDto(List<Product> product);

    @Mappings({
            @Mapping(target = "uuid", source = "id"),
            @Mapping(target = "id", ignore = true)
    })
    Product toEntity(ProductDto dto);

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    ProductSimplifiedDto toSimplifiedDto(Product product);

    List<ProductDto> toSimplifiedDto(List<ProductSimplifiedDto> product);

}
