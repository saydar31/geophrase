package ru.itis.geophrase.service;

import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.TweetDto;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;

import java.util.List;

public interface TweetService {
    Message postTweet(User user, TweetDto tweetDto);

    Message replyTweet(String id, User user, TweetDto tweetDto);

    List<Message> getTweetsInRadius(Location location);
}
