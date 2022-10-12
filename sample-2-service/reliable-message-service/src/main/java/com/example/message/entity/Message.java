package com.example.message.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("message")
public class Message implements Serializable {
    public static final Integer SENDING = 0;    // 发送中：消息还未送到 RabbitMQ Server
    public static final Integer SEND = 1;       // 已成功：消息已经送到了 RabbitMQ Server
    public static final Integer RECEIVED = 2;   // 已接收：消息已经送到了消费者。

    @TableId(type = IdType.AUTO)
    private Long id;
    private String exchange;        // 要发往的交换机
    private String routingKey;      // 消息的路由键
    private String messageContent;  // 消息的具体内容
    // 消息的状态：
    private Integer status;
    private Integer retryCount;     // 消息的重试次数

    private static final long serialVersionUID = 1L;

    public Message(String routingKey, String messageContent) {
        this(null, "", routingKey, messageContent, SENDING, 5);
    }

    public Message(String exchange, String routingKey, String messageContent) {
        this(null, exchange, routingKey, messageContent, SENDING, 5);
    }

}
