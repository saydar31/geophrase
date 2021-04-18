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
import ru.itis.geophrase.dto.MessageDto;
import ru.itis.geophrase.dto.MessageThreadDto;
import ru.itis.geophrase.model.Message;
import ru.itis.geophrase.model.User;
import ru.itis.geophrase.service.MessageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/message")
@Api(value = "Сообщения")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "запостить сообщение", response = MessageDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<?> postTweet(@RequestBody @Valid MessageDto messageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Message message = messageService.postTweet(user, messageDto);
        MessageDto resultDto = modelMapper.map(message, MessageDto.class);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping("/{parentMessageId}/reply")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "ответить на сообщение", response = MessageDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<?> replyTweet(@PathVariable("parentMessageId") String parentId, @RequestBody @Valid MessageDto messageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Message message = messageService.replyTweet(parentId, user, messageDto);
        MessageDto resultDto = modelMapper.map(message, MessageDto.class);
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/inLocation")
    @ApiOperation(value = "получить твиты в радиусе", response = MessageThreadDto.class)
    public ResponseEntity<?> getTweetsInRadius(Location location) {
        List<Message> messageList = messageService.getTweetsInRadius(location);
        List<MessageThreadDto> messageDtoList = messageList.stream()
                .map(tweet -> modelMapper.map(tweet, MessageThreadDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageDtoList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "удалить запись", response = MessageDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", required = true)
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        messageService.delete(id, user);
        return ResponseEntity.ok().build();
    }
}
