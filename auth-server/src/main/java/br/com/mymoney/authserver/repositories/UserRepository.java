package br.com.mymoney.authserver.repositories;

import br.com.mymoney.authserver.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstUserByEmail(String email);
    Optional<User> findFirstUserByUuid(UUID uuid);
}
