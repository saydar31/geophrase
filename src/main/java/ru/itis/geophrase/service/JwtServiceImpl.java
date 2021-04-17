package ru.itis.geophrase.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.repositories.UserRepository;

import java.util.Optional;

@Component
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("ошибка");
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }
        Long id = claims.get("id", Long.class);
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional;
    }

    @Override
    public String getToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
