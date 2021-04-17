package ru.itis.geophrase.service;

import ru.itis.geophrase.model.User;

import java.util.Optional;

public interface JwtService {
    Optional<User> getFromToken(String token);
    String getToken(User user);
}
