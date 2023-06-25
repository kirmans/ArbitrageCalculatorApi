package com.example.arbitrage.Service.Interface;

import com.example.arbitrage.model.ExchangeParity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IParityCalculationService {

    List<ExchangeParity> getAllExchangeParity();
}
