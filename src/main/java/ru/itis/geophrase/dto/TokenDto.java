package ru.itis.geophrase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {
    @ApiModelProperty(notes = "токен для дальнейших запросов")
    private String token;
}
