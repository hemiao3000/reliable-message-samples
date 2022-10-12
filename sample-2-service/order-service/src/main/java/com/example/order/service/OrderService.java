package com.example.order.service;

import com.example.order.dao.OrderMapper;
import com.example.order.entity.Order;
import com.example.order.http.ReliableMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ReliableMessageService messageService;

    @SneakyThrows
    @Transactional
    public Long create(Order order) {
        log.info("创建订单.");
        orderMapper.insert(order);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userId", order.getUserId());
        map.put("money", order.getMoney());

        String messageStr = new ObjectMapper().writeValueAsString(map);

        log.info("扣减用户账户余额.");
        messageService.sendMessage(null, "account.deduct", messageStr);

        log.info("扣减库存成功.");
        map.clear();
        map.put("commodityCode", order.getCommodityCode());
        map.put("count", order.getCount());
        messageStr = new ObjectMapper().writeValueAsString(map);

        messageService.sendMessage(null, "storage.deduct", messageStr);

        return order.getId();
    }

}
