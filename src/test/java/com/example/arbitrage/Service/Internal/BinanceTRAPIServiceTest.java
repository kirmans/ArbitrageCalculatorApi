package com.example.arbitrage.Service.Internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BinanceTRAPIServiceTest {
    @Mock
    private BinanceTRAPIService mockBinanceTRAPIService;

    private BinanceTRAPIService binanceTRAPIService;
    @BeforeEach
    private void setUp() {
        binanceTRAPIService = new BinanceTRAPIService();
    }


    @Test
    void getAllCurrency() {
        Map<String, BigDecimal> currencyPricesMap  = binanceTRAPIService.getAllCurrency();

        //assert
        assertNotNull(currencyPricesMap);
        assertFalse(currencyPricesMap.isEmpty());
        assertTrue(currencyPricesMap.containsKey("USDTTRY"));
        assertNotNull(currencyPricesMap.get("BTCUSDT"));
    }

    @Test
    void getAllCurrency_mock() {
        Map<String, BigDecimal> expected = Map.of("BTCTRY", BigDecimal.valueOf(700000),
                "USDTTRY", BigDecimal.valueOf(25));
        Mockito.when(mockBinanceTRAPIService.getAllCurrency()).thenReturn(expected);
        Map<String, BigDecimal> actual = mockBinanceTRAPIService.getAllCurrency();
        assertEquals(expected, actual);
    }
}