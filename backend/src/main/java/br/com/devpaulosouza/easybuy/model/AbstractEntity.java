package br.com.devpaulosouza.easybuy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Column
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.getUuid())) {
            this.setUuid(UUID.randomUUID());
        }
    }

}
