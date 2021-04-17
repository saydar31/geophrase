package ru.itis.geophrase.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.geophrase.dto.UserDto;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "Информация о пользователях")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/nicknameIsBusy")
    @ApiOperation(value = "Проверить ник на занятость", response = Boolean.class)
    public ResponseEntity<Boolean> checkNicknameBusy(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(userService.nickNameIsBusy(nickname));
    }

    @GetMapping("/currentUser")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "получить информацию о текущем пользователе", response = UserDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<UserDto> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

}
