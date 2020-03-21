package br.com.devpaulosouza.easybuy.repository;

import br.com.devpaulosouza.easybuy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    User findByUsername(String username);

    User findByToken(UUID token);

}
