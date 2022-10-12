package com.example.order.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("reliable-message-service")
public interface ReliableMessageService {

    @RequestMapping("/message/send")
    public String sendMessage(@RequestParam(value = "exchange") String exchange,
                              @RequestParam("routingKey") String routingKey,
                              @RequestParam("messageContent") String messageContent);

    @RequestMapping("/message/received")
    public String receivedMessage(@RequestParam("id") Long id);
}
