package ru.itis.geophrase.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.geophrase.dto.Location;
import ru.itis.geophrase.dto.TweetDto;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.service.TweetService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tweet")
@Api(value = "Твиты")
public class TweetController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "запостить твит", response = TweetDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<?> postTweet(@RequestBody @Valid TweetDto tweetDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Message message = tweetService.postTweet(user, tweetDto);
        TweetDto resultDto = modelMapper.map(message, TweetDto.class);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/{parentTweetId}/reply")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "ответить на твит", response = TweetDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<?> replyTweet(@PathVariable("parentTweetId") String parentId, @RequestBody @Valid TweetDto tweetDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Message message = tweetService.replyTweet(parentId, user, tweetDto);
        TweetDto resultDto = modelMapper.map(message, TweetDto.class);
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/inLocation")
    @ApiOperation(value = "получить твиты в радиусе", response = TweetDto.class)
    public ResponseEntity<?> getTweetsInRadius(Location location) {
        List<Message> messageList = tweetService.getTweetsInRadius(location);
        List<TweetDto> tweetDtoList = messageList.stream()
                .map(tweet -> modelMapper.map(tweet, TweetDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tweetDtoList);
    }
}
