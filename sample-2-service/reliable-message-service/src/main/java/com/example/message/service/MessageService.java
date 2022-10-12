package com.example.message.service;

import com.example.message.dao.MessageMapper;
import com.example.message.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper messageMapper;

    public void storeMessage(String exchange, String routingKey, String messageContent) {
        Message message = new Message(exchange, routingKey, messageContent);
        messageMapper.insert(message);
    }

    public void confirmMessage(Long id) {
        Message message = messageMapper.selectById(id);
        message.setStatus(Message.RECEIVED);
        messageMapper.updateById(message);
    }
}
