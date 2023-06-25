package com.example.arbitrage.controller;

import com.example.arbitrage.Service.Interface.IParityCalculationService;
import com.example.arbitrage.Service.Internal.ParityCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParityController {
    @Autowired
    IParityCalculationService parityCalculationService;
    @GetMapping("/user")
    public ResponseEntity<String> getAllDifferentParity() {
        String content = parityCalculationService.getAllCurrency();
        return ResponseEntity.ok(content);
    }
}
