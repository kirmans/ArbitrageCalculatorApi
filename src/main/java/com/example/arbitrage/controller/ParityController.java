package com.example.arbitrage.controller;

import com.example.arbitrage.Service.Interface.IParityCalculationService;
import com.example.arbitrage.Service.Internal.ParityCalculationService;
import com.example.arbitrage.model.ExchangeParity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParityController {
    @Autowired
    IParityCalculationService parityCalculationService;
    @GetMapping("/user")

    public ResponseEntity<List<ExchangeParity>> getAllDifferentParity() {
        List<ExchangeParity> exhaParityList = parityCalculationService.getAllExchangeParity();

        return ResponseEntity.ok(exhaParityList);
    }
}
