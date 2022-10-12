package com.example.order.config;

import com.example.order.dao.MessageMapper;
import com.example.order.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableRabbit
@RequiredArgsConstructor
@EnableScheduling
public class RabbitConfig { // 配置类本身也是 Spring IoC 容器中的一个单例对象。所以它也可以依赖注入

    private final MessageMapper messageMapper;

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        // 当 Exchange 收到消息后，这里设置的回调方法会被触发执行
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String idStr = correlationData.getId();
            Long id = Long.parseLong(idStr);        // String -> Long
            Message message = messageMapper.selectById(id);

            if (ack) {
                // 发送成功，修改消息状态。
                message.setStatus(Message.SEND);
            }
            // 发送失败的消息： status = '发送中' and retry_count = 0;
            message.setRetryCount(message.getRetryCount() - 1);
            messageMapper.updateById(message);
        });
        return rabbitTemplate;
    }
}
