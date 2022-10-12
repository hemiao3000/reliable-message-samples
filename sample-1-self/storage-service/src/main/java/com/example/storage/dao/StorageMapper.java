package com.example.storage.dao;

import com.example.storage.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

    @Update("update `storage` set `count` = `count` - #{count} where `commodity_code` = #{code}")
    int deduct(@Param("code") String commodityCode, @Param("count") int count);
}
