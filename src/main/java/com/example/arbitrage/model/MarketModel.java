package com.example.arbitrage.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MarketModel {
    private String name;
    Map<String, BigDecimal> currencyPricesMap;

    public MarketModel(String name, Map<String, BigDecimal> currencyPricesMap) {
        this.name = name;
        this.currencyPricesMap = currencyPricesMap;
    }

    public MarketModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, BigDecimal> getCurrencyPricesMap() {
        return currencyPricesMap;
    }

    public void setCurrencyPricesMap(Map<String, BigDecimal> currencyPricesMap) {
        this.currencyPricesMap = currencyPricesMap;
    }
}
