package com.example.account.reciver;

import com.example.account.dao.ReceivedMessageMapper;
import com.example.account.entity.ReceivedMessage;
import com.example.account.http.ReliableMessageService;
import com.example.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountDeductMessageReceiver {

    private final ReceivedMessageMapper receivedMessageMapper;
    private final AccountService accountService;
    private final ReliableMessageService messageService;

    @SneakyThrows
    @Transactional
    @RabbitListener(queues = "account.deduct")
    public void handle(String msg) {

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

        String userId= (String) map.get("userId");
        Integer money = (Integer) map.get("money");
        System.out.println("扣减用户 " + userId + " " + money + "元钱");

        // 第 2 步：执行业务逻辑，扣件库存
        accountService.deduct(userId, money);
        log.debug("业务逻辑执行成功");

        messageService.receivedMessage(Long.parseLong(messageIdStr));
    }
}
