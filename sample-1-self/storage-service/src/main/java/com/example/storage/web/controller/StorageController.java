package com.example.storage.web.controller;

import com.example.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/storage/deduct")
    public ResponseEntity<Void> deduct(@RequestParam("code") String code,
                                       @RequestParam("count") Integer count) {
        storageService.deduct(code, count);
        return ResponseEntity.noContent().build();
    }
}
