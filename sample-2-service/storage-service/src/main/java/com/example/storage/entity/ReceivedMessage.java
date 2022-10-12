package com.example.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("received_message")
public class ReceivedMessage implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private static final long serialVersionUID = 1L;
}