package com.example.order.web.controller;

import com.example.order.service.MessageService;
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

    @RequestMapping("/message/received")
    public String receivedMessage(@RequestParam("id") Long id) {
        messageService.confirmMessage(id);
        return "success";
    }
}
