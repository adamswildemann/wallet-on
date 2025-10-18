package br.com.walleton.repository;

import br.com.walleton.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
