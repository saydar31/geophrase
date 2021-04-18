package ru.itis.geophrase.service;

import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.MessageDto;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;

import java.util.List;

public interface MessageService {
    Message postTweet(User user, MessageDto messageDto);

    Message replyTweet(String id, User user, MessageDto messageDto);

    List<Message> getTweetsInRadius(Location location);

    void delete(String id, User user);
}
