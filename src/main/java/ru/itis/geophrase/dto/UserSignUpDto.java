package ru.itis.geophrase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignUpDto {
    @ApiModelProperty(notes = "ник, уникальный")
    private String nickname;
    @ApiModelProperty(notes = "пароль")
    private String password;
}
