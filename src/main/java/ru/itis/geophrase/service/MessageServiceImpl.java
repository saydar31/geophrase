package ru.itis.geophrase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.TweetDto;
import ru.itis.geophrase.exception.TweetNotFoundException;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.repositories.TweetRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Message postTweet(User user, TweetDto tweetDto) {
        Message message = Message.builder()
                .user(user)
                .content(tweetDto.getContent())
                .lat(tweetDto.getLat())
                .lon(tweetDto.getLon())
                .build();
        return tweetRepository.save(message);
    }

    @Override
    @Transactional
    public Message replyTweet(String parentTweetId, User user, TweetDto tweetDto) {
        Message parentMessage = tweetRepository.findById(parentTweetId).orElseThrow(TweetNotFoundException::new);
        Message message = Message.builder()
                .user(user)
                .content(tweetDto.getContent())
                .parentMessage(parentMessage)
                .build();
        tweetRepository.save(message);
        parentMessage.getChildMessages().add(message);
        return message;
    }


    //радиус поиска в километрах
    private static final double RADIUS = 5;

    @Override
    public List<Message> getTweetsInRadius(Location location) {
        return tweetRepository.findAllInRadius(location.getLat(), location.getLon(), RADIUS);
    }
}
