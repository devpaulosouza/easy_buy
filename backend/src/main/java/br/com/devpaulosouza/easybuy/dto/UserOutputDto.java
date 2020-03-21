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

    @Length(max = 255)
    private String name;

    @Length(max = 128)
    private String username;

    private UUID token;

}
