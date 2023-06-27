package com.example.arbitrage.Service.Internal;

import com.example.arbitrage.Service.Interface.IMarketsApiService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class BinanceAPIService implements IMarketsApiService {
    private final static  String apiEndpoint =  "https://api.binance.com/api/v3/ticker/price";
    private final static  String baseCurrency =  "USDT";
    @Override
    public Map<String, BigDecimal> getAllCurrency() {
        HttpURLConnection connection = null;
        Map<String, BigDecimal> currencyPricesMap = new HashMap<>();
        try {
            URL url = new URL(apiEndpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String responseData = response.toString();

                // Filter and extract USD prices
                String[] currencyData = responseData.split("\\{");
                for (String currency : currencyData) {
                    if (currency.contains(baseCurrency)) {
                        String[] parts = currency.split("\"");
                        String symbol = parts[3];
                        String price = parts[7];
                        currencyPricesMap.put(symbol, new BigDecimal(price));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return currencyPricesMap;
    }
}
