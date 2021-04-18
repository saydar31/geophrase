package ru.itis.geophrase.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.MessageDto;
import ru.itis.geophrase.exception.TweetNotFoundException;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.repositories.MessageRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message postTweet(User user, MessageDto messageDto) {
        Message message = Message.builder()
                .user(user)
                .content(messageDto.getContent())
                .createdAt(Instant.now())
                .lastUpdatedAt(Instant.now())
                .lat(messageDto.getLat())
                .lon(messageDto.getLon())
                .build();
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public Message replyTweet(String parentTweetId, User user, MessageDto messageDto) {
        Message parentMessage = messageRepository.findById(parentTweetId).orElseThrow(TweetNotFoundException::new);
        Message message = Message.builder()
                .user(user)
                .lastUpdatedAt(Instant.now())
                .content(messageDto.getContent())
                .parentMessage(parentMessage)
                .build();
        messageRepository.save(message);
        parentMessage.getChildMessages().add(message);
        return message;
    }


    //радиус поиска в километрах
    private static final double RADIUS = 5;

    @Override
    public List<Message> getTweetsInRadius(Location location) {
        List<Message> allInRadius = messageRepository.findAllInRadius(location.getLat(), location.getLon(), RADIUS);
        for (Message message : allInRadius) {
            initializeTweet(message);
        }
        return allInRadius;
    }

    private void initializeTweet(Message message) {
        Hibernate.initialize(message.getChildMessages());
        for (Message childMessage : message.getChildMessages()) {
            initializeTweet(childMessage);
        }
    }


    @Override
    @Transactional
    public void delete(String id, User user) {
        Message message = messageRepository.findById(id).orElseThrow(TweetNotFoundException::new);
        if (user.equals(message.getUser())){
            messageRepository.delete(message);
        } else {
            throw new AccessDeniedException("это сообщение принадлежит другому");
        }
    }
}
