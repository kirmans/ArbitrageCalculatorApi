package com.example.arbitrage.model;

import java.math.BigDecimal;

//@Document(collection="ExchangeParity")
public class ExchangeParity {
    private String exchangeName;
    private String currency;
    private BigDecimal price;

    public ExchangeParity(String exchangeName, String currency) {
        this.exchangeName = exchangeName;
        this.currency = currency;
    }

    public ExchangeParity(String exchangeName, String currency, BigDecimal price) {
        this.exchangeName = exchangeName;
        this.currency = currency;
        this.price = price;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
