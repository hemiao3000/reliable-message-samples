package com.example.message.web.controller;

import com.example.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @RequestMapping("/message/send")
    public String sendMessage(@RequestParam(value = "exchange", required = false) String exchange,
                              @RequestParam("routingKey") String routingKey,
                              @RequestParam("messageContent") String messageContent) {
        messageService.storeMessage(exchange, routingKey, messageContent);
        return "success";
    }

    @RequestMapping("/message/received")
    public String receivedMessage(@RequestParam("id") Long id) {
        messageService.confirmMessage(id);
        return "success";
    }
}
