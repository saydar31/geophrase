package ru.itis.geophrase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.geophrase.repositories.UserRepository;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean nickNameIsBusy(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
