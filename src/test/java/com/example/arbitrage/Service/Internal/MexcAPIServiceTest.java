package com.example.arbitrage.Service.Internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MexcAPIServiceTest {
    @Mock
    MexcAPIService mexcAPIServiceMock;

    MexcAPIService mexcAPIService;
    @BeforeEach
    private void setUp() {
        mexcAPIService = new MexcAPIService();
    }

    @Test
    void testGetAllCurrency() {
        Map<String, BigDecimal> currencyPricesMap = mexcAPIService.getAllCurrency();

        //assert
        assertNotNull(currencyPricesMap);
        assertFalse(currencyPricesMap.isEmpty());
        assertTrue(currencyPricesMap.containsKey("BTCUSDT"));
        assertNotNull(currencyPricesMap.get("BTCUSDT"));

    }

    @Test
    void testGetAllCurrency_mock() {
        Map<String, BigDecimal> expected = Map.of("BTCUSDT", BigDecimal.valueOf(30000),
                "ETHUSDT", BigDecimal.valueOf(18000));

        Mockito.when(mexcAPIServiceMock.getAllCurrency()).thenReturn(expected);

        Map<String, BigDecimal> actual = mexcAPIServiceMock.getAllCurrency();

        assertEquals(expected, actual);
    }
}