package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInputDto implements Dto {

    @NotEmpty
    @Length(min = 10, max = 255)
    private String name;

    @NotEmpty
    @Length(min = 10, max = 128)
    private String username;

    @NotEmpty
    @Length(min = 5, max = 64)
    private String password;

}
