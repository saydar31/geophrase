package ru.itis.geophrase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @ApiModelProperty(notes = "идентификатор пользователя (целое положительное число)")
    private Long id;
    @ApiModelProperty(notes = "ник, уникальный")
    private String nickname;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(notes = "токен для дальнейших запросов")
    private String password;
}
