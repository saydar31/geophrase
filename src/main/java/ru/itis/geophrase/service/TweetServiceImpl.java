package ru.itis.geophrase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.TweetDto;
import ru.itis.geophrase.exception.TweetNotFoundException;
import ru.itis.geophrase.model.Tweet;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.repositories.TweetRepository;

import java.util.List;

@Component
public class TweetServiceImpl implements TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet postTweet(User user, TweetDto tweetDto) {
        Tweet tweet = Tweet.builder()
                .user(user)
                .content(tweetDto.getContent())
                .lat(tweetDto.getLat())
                .lon(tweetDto.getLon())
                .build();
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet replyTweet(String parentTweetId, User user, TweetDto tweetDto) {
        Tweet parentTweet = tweetRepository.findById(parentTweetId).orElseThrow(TweetNotFoundException::new);
        Tweet tweet = Tweet.builder()
                .user(user)
                .content(tweetDto.getContent())
                .parentTweet(parentTweet)
                .build();
        return tweetRepository.save(tweet);
    }


    //радиус поиска в километрах
    private static final double RADIUS = 5;

    @Override
    public List<Tweet> getTweetsInRadius(Location location) {
        return tweetRepository.findAllInRadius(location.getLat(), location.getLon(), RADIUS);
    }
}
