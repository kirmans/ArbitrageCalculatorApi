package com.example.arbitrage.Service.Internal;

import com.example.arbitrage.Service.Interface.IParityCalculationService;

import com.example.arbitrage.model.ExchangeParity;
import com.example.arbitrage.model.MarketModel;
import com.example.arbitrage.model.PriceNameMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class ParityCalculationService implements IParityCalculationService {
    @Autowired
    private Environment env;
    @Autowired
    BinanceAPIService binanceAPIService;
    @Autowired
    MexcAPIService mexcAPIService;
    @Autowired
    BinanceTRAPIService binanceTRAPIService;

    private final BigDecimal threshHold = new BigDecimal("10");
    private final BigDecimal buyAmount = new BigDecimal("1000");

    @Override
    public List<ExchangeParity> getAllExchangeParity() {
        List<ExchangeParity> buyableParities = new ArrayList<>();

        try {
            MarketModel binance = new MarketModel("Binance");
            MarketModel mexc = new MarketModel("mexc");
            MarketModel binancetr = new MarketModel("binancetr");
            // set currency price
            binance.setCurrencyPricesMap(binanceAPIService.getAllCurrency());
            mexc.setCurrencyPricesMap(mexcAPIService.getAllCurrency());
            binancetr.setCurrencyPricesMap(binanceTRAPIService.getAllCurrency());

            List<MarketModel> marketModelList = new ArrayList<>();
            marketModelList.add(binance);
            marketModelList.add(mexc);
            marketModelList.add(binancetr);
            buyableParities = calculateDifferentOfMarkets(marketModelList);

        } catch (Exception e) {
            System.out.println("Error: Invalid URL " + e);
        }

        return buyableParities;
    }

    private List<ExchangeParity> calculateDifferentOfMarkets(List<MarketModel> marketModelList)
    {
        List<ExchangeParity> buyableParities = new ArrayList<>();
        Map<String, List<PriceNameMap>> priceNameMap = new HashMap<>();
        // Add all parity and price
        for(MarketModel market : marketModelList)
        {
            String marketName = market.getName();
            for(Map.Entry<String, BigDecimal> entry : market.getCurrencyPricesMap().entrySet())
            {
                String parityName = entry.getKey();
                BigDecimal parityPrice = entry.getValue();
                priceNameMap.computeIfAbsent(parityName, k-> new ArrayList<PriceNameMap>()).add(new PriceNameMap(marketName, parityPrice));
            }
        }

        for(String key : priceNameMap.keySet())
        {
            List<PriceNameMap> priceNameList = priceNameMap.get(key);

            for(int i = 0; i < priceNameList.size(); i++)
            {
                for(int j = i+1; j < priceNameList.size(); j++)
                {
                    PriceNameMap priceNameMap1 = priceNameList.get(i);
                    PriceNameMap priceNameMap2 = priceNameList.get(j);
                    BigDecimal priceDifferent = priceNameMap1.getPrice().subtract(priceNameMap2.getPrice());
                    BigDecimal control = buyAmount.multiply(priceDifferent.abs()); //remove after test
                    if(buyAmount.multiply(priceDifferent.abs()).compareTo(threshHold) > -1)
                    {
                        buyableParities.add(new ExchangeParity(priceNameMap1.getExchangeName(), key,
                                priceNameMap1.getPrice(), priceNameMap2.getExchangeName(), priceNameMap2.getPrice()));
                    }
                }
            }
        }
        return buyableParities;
    }

    public String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", env.getProperty("coinMarketGapApi.key"));

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

}
