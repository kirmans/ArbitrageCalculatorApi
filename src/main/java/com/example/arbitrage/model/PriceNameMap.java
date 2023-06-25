package com.example.arbitrage.model;

import java.math.BigDecimal;

public class PriceNameMap {
    private String exchangeName;

    private BigDecimal price;

    public PriceNameMap(String exchangeName, BigDecimal priceLower) {
        this.exchangeName = exchangeName;
        this.price = priceLower;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
