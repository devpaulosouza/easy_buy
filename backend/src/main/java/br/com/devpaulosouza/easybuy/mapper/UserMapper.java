package br.com.devpaulosouza.easybuy.mapper;

import br.com.devpaulosouza.easybuy.dto.UserInputDto;
import br.com.devpaulosouza.easybuy.dto.UserOutputDto;
import br.com.devpaulosouza.easybuy.dto.UserOutputSimplifiedDto;
import br.com.devpaulosouza.easybuy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    UserOutputDto toDto(User user);

    @Mappings({
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", ignore = true),

    })
    User toEntity(UserInputDto userInputDto);

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    UserOutputSimplifiedDto toSimplifiedDto(User user);

}
