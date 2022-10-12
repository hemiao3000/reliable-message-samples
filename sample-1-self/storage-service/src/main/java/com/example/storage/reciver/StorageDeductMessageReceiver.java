package com.example.storage.reciver;

import com.example.storage.dao.ReceivedMessageMapper;
import com.example.storage.dao.StorageMapper;
import com.example.storage.entity.ReceivedMessage;
import com.example.storage.http.OrderServiceClient;
import com.example.storage.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageDeductMessageReceiver {

    private final ReceivedMessageMapper receivedMessageMapper;
    private final StorageService storageService;
    private final OrderServiceClient orderServiceClient;

    @SneakyThrows
    @Transactional
    @RabbitListener(queues = "storage.deduct")
    public void handle(String msg
//                       Channel channel,
//                       @Header(AmqpHeaders.DELIVERY_TAG) long tag
                       ) {

//        channel.basicAck(tag, false);

        String[] split = StringUtils.split(msg, ":");
        String messageIdStr = split[0];
        String jsonStr = split[1];

        // 第 1 步：消息去重
        // 这句话（sql语句）执行失败，会抛出异常，代码路程就不再继续执行。出现这种情况就意味着我们收到了重复消息。
        ReceivedMessage receivedMessage = new ReceivedMessage(Long.parseLong(messageIdStr));
        try {
            receivedMessageMapper.insert(receivedMessage);
        } catch (RuntimeException e) {
            System.out.println("存储消息失败，可能是因为重复消息。");
            return;
        }

        Map map = new ObjectMapper().readValue(jsonStr, Map.class);

        String commodityCode = (String) map.get("commodityCode");
        Integer count = (Integer) map.get("count");

        // 第 2 步：执行业务逻辑，扣件库存
        storageService.deduct(commodityCode, count);
        log.debug("业务逻辑执行成功");

        orderServiceClient.receivedMessage(Long.parseLong(messageIdStr));
    }
}
