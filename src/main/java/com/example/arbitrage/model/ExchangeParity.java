package com.example.arbitrage.model;

import java.math.BigDecimal;

//@Document(collection="ExchangeParity")
public class ExchangeParity {
    private String exchangeNameFirst;
    private String currency;
    private BigDecimal priceFirst;

    private String exchangeNameSecond;
    private BigDecimal priceSecond;

    public ExchangeParity(String exchangeNameFirst, String currency, BigDecimal priceFirst, String exchangeNameSecond, BigDecimal priceSecond) {
        this.exchangeNameFirst = exchangeNameFirst;
        this.currency = currency;
        this.priceFirst = priceFirst;
        this.exchangeNameSecond = exchangeNameSecond;
        this.priceSecond = priceSecond;
    }


    public String getExchangeNameFirst() {
        return exchangeNameFirst;
    }

    public void setExchangeNameFirst(String exchangeNameFirst) {
        this.exchangeNameFirst = exchangeNameFirst;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPriceFirst() {
        return priceFirst;
    }

    public void setPriceFirst(BigDecimal priceFirst) {
        this.priceFirst = priceFirst;
    }

    public String getExchangeNameSecond() {
        return exchangeNameSecond;
    }

    public void setExchangeNameSecond(String exchangeNameSecond) {
        this.exchangeNameSecond = exchangeNameSecond;
    }

    public BigDecimal getPriceSecond() {
        return priceSecond;
    }

    public void setPriceSecond(BigDecimal priceSecond) {
        this.priceSecond = priceSecond;
    }
}
