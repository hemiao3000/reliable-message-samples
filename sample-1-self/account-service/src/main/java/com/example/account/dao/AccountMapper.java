package com.example.account.dao;

import com.example.account.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Update("update `account` set `money` = `money` - ${money} where `user_id` = #{userId}")
    int deduct(@Param("userId") String userId, @Param("money") int money);

}
