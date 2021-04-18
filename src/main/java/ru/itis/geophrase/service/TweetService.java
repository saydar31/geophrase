package ru.itis.geophrase.service;

import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.TweetDto;
import ru.itis.geophrase.model.Tweet;
import ru.itis.geophrase.model.User;

import java.util.List;

public interface TweetService {
    Tweet postTweet(User user, TweetDto tweetDto);

    Tweet replyTweet(String id, User user, TweetDto tweetDto);

    List<Tweet> getTweetsInRadius(Location location);
}
