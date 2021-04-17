package ru.itis.geophrase.service;

import ru.itis.geophrase.dto.SignInDto;
import ru.itis.geophrase.dto.TokenDto;
import ru.itis.geophrase.dto.UserDto;
import ru.itis.geophrase.model.User;

public interface AuthService {
    User signUp(UserDto userDto);
    TokenDto signIn(SignInDto signInDto);
}
