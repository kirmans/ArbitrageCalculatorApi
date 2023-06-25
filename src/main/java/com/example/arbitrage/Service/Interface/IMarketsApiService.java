package com.example.arbitrage.Service.Interface;

import java.math.BigDecimal;
import java.util.Map;

public interface IMarketsApiService {
    Map<String, BigDecimal> getAllCurrency();
}
