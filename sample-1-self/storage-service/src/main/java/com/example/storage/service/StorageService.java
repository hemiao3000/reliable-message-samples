package com.example.storage.service;

import com.example.storage.dao.StorageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageMapper storageMapper;

    @Transactional
    public void deduct(String commodityCode, int count) {
        log.info("开始扣减库存");
        storageMapper.deduct(commodityCode, count);
        log.info("扣减库存成功");
    }
}
