package com.example.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}