package com.example.arbitrage.Service.Internal;

import com.example.arbitrage.Service.Interface.IMarketsApiService;
import com.example.arbitrage.Service.Interface.IParityCalculationService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Service
public class BinanceTRAPIService implements IMarketsApiService {
    private final static  String baseCurrency =  "USDT";
    @Override
    public Map<String, BigDecimal> getAllCurrency() {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.binance.me/api/v3/ticker/price");
        StringBuilder sb = new StringBuilder();
        Map<String, BigDecimal> currencyPricesMap = new HashMap<>();
        try {
            HttpResponse response = client.execute(request);
            String responseData = EntityUtils.toString(response.getEntity());

            JSONArray jsonArray = new JSONArray(responseData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String symbol = jsonObject.getString("symbol");
                if (symbol.contains(baseCurrency))
                {
                    String price = jsonObject.getString("price");
                    System.out.println("Symbol: " + symbol + ", Price: " + price);
                    currencyPricesMap.put(symbol, new BigDecimal(price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyPricesMap;
    }
}
