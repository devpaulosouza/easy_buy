package br.com.devpaulosouza.easybuy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLoginDto {

    @NotEmpty
    @Length(min = 5, max = 128)
    private String username;

    @NotEmpty
    @Length(min = 5, max = 64)
    private String password;

}
