package com.example.arbitrage.Service.Internal;

import com.example.arbitrage.Service.Interface.IParityCalculationService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Service
public class ParityCalculationService implements IParityCalculationService {
    @Autowired
    private Environment env;
    @Autowired
    BinanceAPIService binanceAPIService;

    @Autowired
    MexcAPIService mexcAPIService;
    @Override
    public String getAllCurrency() {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=1000&convert=USD&sort=market_cap&sort_dir=desc&aux=cmc_rank,platform";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        String result = null;
        try {
            result = makeAPICall(uri, paratmers);
            //result = binanceAPIService.getAllCurrency();
            //result = mexcAPIService.getAllCurrency();
        } catch (Exception e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }

        return result;
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
