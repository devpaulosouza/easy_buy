package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserOutputSimplifiedDto {

    private UUID id;

    private String name;

    private String username;

}
