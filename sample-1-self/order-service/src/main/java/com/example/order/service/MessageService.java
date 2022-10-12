package com.example.order.service;

import com.example.order.dao.MessageMapper;
import com.example.order.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper messageMapper;

    public void confirmMessage(Long id) {
        Message message = messageMapper.selectById(id);
        message.setStatus(Message.RECEIVED);
        messageMapper.updateById(message);
    }
}
