package ru.itis.geophrase.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.exception.TweetNotFoundException;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.service.JwtService;

import java.util.Optional;

@Component
@Slf4j
public class JwtAuthenticationProvider extends TweetNotFoundException implements AuthenticationProvider {
    @Autowired
    private JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> userOptional = jwtService.getFromToken(authentication.getCredentials().toString());
        if (userOptional.isPresent()){
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
            jwtAuthentication.setUser(userOptional.get());
            return jwtAuthentication;
        } else {
            log.error("плохой токен найден");
            throw new BadCredentialsException("bad token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
