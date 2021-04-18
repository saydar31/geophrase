package ru.itis.geophrase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.dto.SignInDto;
import ru.itis.geophrase.dto.TokenDto;
import ru.itis.geophrase.dto.UserDto;
import ru.itis.geophrase.dto.UserSignUpDto;
import ru.itis.geophrase.exception.NicknameExistsException;
import ru.itis.geophrase.exception.WrongNicknameOrPasswordExcpetion;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.repositories.UserRepository;

import java.util.Optional;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public User signUp(UserSignUpDto userDto) {
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new NicknameExistsException();
        }
        User user = User.builder()
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        String nickname = signInDto.getNickname();
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        User user = userOptional.orElseThrow(WrongNicknameOrPasswordExcpetion::new);
        if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            return new TokenDto(jwtService.getToken(user));
        } else {
            throw new WrongNicknameOrPasswordExcpetion();
        }
    }
}
