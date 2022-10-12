package com.example.storage.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("order-service")
public interface OrderServiceClient {

    @PostMapping("/message/received")
    public String receivedMessage(@RequestParam("id") Long id)  ;
}
