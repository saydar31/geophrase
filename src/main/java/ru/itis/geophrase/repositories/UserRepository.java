package ru.itis.geophrase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.geophrase.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);
}
