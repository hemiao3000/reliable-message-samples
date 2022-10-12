package com.example.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}