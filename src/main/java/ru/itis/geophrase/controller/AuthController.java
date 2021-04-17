package ru.itis.geophrase.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.geophrase.dto.SignInDto;
import ru.itis.geophrase.dto.TokenDto;
import ru.itis.geophrase.dto.UserDto;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "Авторизация / регистрация")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signUp")
    @ApiOperation(value = "Запрос на авторизацию", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Регистрация успешна"),
            @ApiResponse(code = 400, message = "Ник уже занят"),
    })
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        User user = authService.signUp(userDto);
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @PostMapping("/signIn")
    @ApiOperation(value = "Запрос на авторизацию", response = TokenDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Авторизация успешна"),
            @ApiResponse(code = 400, message = "неверный ник или пароль"),
    })
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        TokenDto tokenDto = authService.signIn(signInDto);
        return ResponseEntity.ok(tokenDto);
    }
}
