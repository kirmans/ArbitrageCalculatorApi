package com.example.arbitrage.Service.Internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BinanceAPIServiceTest {
    private BinanceAPIService binanceAPIService;

    @Mock
    private BinanceAPIService mockbinanceAPIService;
    @BeforeEach
    public void setUp() {
        binanceAPIService = new BinanceAPIService();
    }

    @Test
    public void testGetAllCurrency() {
        Map<String, BigDecimal> currencyPricesMap = binanceAPIService.getAllCurrency();

        // Assert
        assertNotNull(currencyPricesMap);
        assertFalse(currencyPricesMap.isEmpty());
        assertTrue(currencyPricesMap.containsKey("BTCUSDT"));
        assertNotNull(currencyPricesMap.get("BTCUSDT"));
    }

    @Test
    public void testGetAllMockCurrency() {
        Map<String, BigDecimal> expected = Map.of("BTCUSDT", BigDecimal.valueOf(35000),
                "ETHUSDT", BigDecimal.valueOf(1800));

        Mockito.when(mockbinanceAPIService.getAllCurrency()).thenReturn(expected);
        Map<String, BigDecimal> actual = mockbinanceAPIService.getAllCurrency();

        assertEquals(expected, actual);
    }
}