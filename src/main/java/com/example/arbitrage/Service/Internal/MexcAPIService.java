package com.example.arbitrage.Service.Internal;

import com.example.arbitrage.Service.Interface.IParityCalculationService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class MexcAPIService implements IParityCalculationService {

    @Override
    public String getAllCurrency() {
        String apiEndpoint = "https://api.mexc.com/api/v3/ticker/price";
        String baseCurrency = "USDT";
        String responseData = null;
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                responseData = response.toString();

                // Filter and extract USD values
                String[] currencyData = responseData.split("\"symbol\":");
                for (String currency : currencyData) {
                    if (currency.contains(baseCurrency)) {
                        String[] parts = currency.split("\"");
                        String symbol = parts[1];
                        String price = parts[5];
                        System.out.println(symbol + ": " + price);
                    }
                }
            } else {
                System.out.println("Request failed with status code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData;
    }
}
