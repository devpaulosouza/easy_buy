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
public class UserOutputDto implements Dto {

    private UUID id;

    private String name;

    private String username;

    private UUID token;

    private String role;

}
