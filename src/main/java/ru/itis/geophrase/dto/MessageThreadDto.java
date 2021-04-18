package ru.itis.geophrase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageThreadDto {
    private String id;
    private String userNickname;
    private String parentMessageId;
    private String content;
    private Double lat;
    private Double lon;
    private List<MessageThreadDto> childMessages;
}
