package com.example.order.timer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.order.dao.MessageMapper;
import com.example.order.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMessageTimerTask {

    private final RabbitTemplate rabbitTemplate;
    private final MessageMapper messageMapper;

    @Scheduled(fixedRate = 6000)
    public void handle() {
        log.debug("定时任务开始干活，发送消息...");

        /*
         * 查询待发送消息：status != '发接收' and retry_count > 0
         */
        QueryWrapper<Message> qw = new QueryWrapper<Message>()
                .ne("status", Message.RECEIVED)
                .gt("retry_count", 0);

        List<Message> messageList = messageMapper.selectList(qw);
        if (messageList.isEmpty()) {
            log.debug("没有待发送消息");
            return;
        }

        log.debug("有 {} 条消息等待发送", messageList.size());

        /*
         * 循环遍历，发送消息
         */
        for (Message message : messageList) {
            String exchange = message.getExchange();
            String routingKey = message.getRoutingKey();
            String messageContent = message.getId() + ":" + message.getMessageContent();    // 在要发送的实际内容之前拼上这条消息的ID。
            log.debug("发送消息：[{}] [{}] [{}]", exchange, routingKey, messageContent);

            String idStr = String.valueOf(message.getId()); // Long -> String
            CorrelationData correlationData = new CorrelationData(idStr);

            if (Objects.equals("", message.getExchange())) {
                // 发给默认交换机
                rabbitTemplate.convertAndSend(routingKey, (Object) messageContent, correlationData);
            } else {
                // 发给指定交换机
                rabbitTemplate.convertAndSend(exchange, routingKey, messageContent, correlationData);
            }
        }
    }

}
