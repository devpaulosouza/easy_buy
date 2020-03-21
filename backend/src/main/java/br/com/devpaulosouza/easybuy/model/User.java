package br.com.devpaulosouza.easybuy.model;

import br.com.devpaulosouza.easybuy.enumeration.AuthorityType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_")
@Getter
@Setter
@ToString
public class User extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthorityType role;

    @Column
    private UUID token;

    @Column
    private LocalDateTime tokenValidUntil;

}
