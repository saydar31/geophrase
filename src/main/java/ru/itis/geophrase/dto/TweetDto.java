package ru.itis.geophrase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TweetDto {
    private String id;
    private String userNickname;
    private String parentTweetId;
    private String content;
    private Double lat;
    private Double lon;
}
