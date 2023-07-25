package tech.dinhphu28.springsec.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.dinhphu28.springsec.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
}
