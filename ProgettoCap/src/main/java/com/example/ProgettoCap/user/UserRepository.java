package com.example.ProgettoCap.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);
    public boolean existsByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);
    Optional<User> findOneByUsername(String username);
    //
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    public List<UserResponsePrj> findAllBy();

}
