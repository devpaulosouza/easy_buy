package br.com.devpaulosouza.easybuy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class AbstractEntity implements Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.getUuid())) {
            this.setUuid(UUID.randomUUID());
        }
    }

}
