package com.company;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        String API_KEY = "API_KEY"; // https://rapidapi.com/natkapral/api/currency-converter5
        String FROM = "USD";
        String TO = "TRY";
        float AMOUNT = 15;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from="+FROM+"&to="+TO+"&amount="+AMOUNT)
                        .get()
                        .addHeader("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", API_KEY)
                        .build();

        Response response = client.newCall(request).execute();
        String durum = null;

        if (FROM == "" || FROM == null || TO == "" || TO == null) {
            durum = "FROM_OR_NULL_EMPTY";
        }else if (response.code() != 200) {
            durum = ""+response.code();
        }else{
            durum = "OK";
        }

        if (durum.equals("OK")){
            JSONParser parser = new JSONParser();

            Object jsonObj = parser.parse(response.body().string());

            JSONObject jsonObject = (JSONObject) jsonObj;

            JSONObject name = (JSONObject) jsonObject.get("rates");
            JSONObject asd = (JSONObject)  name.get(TO);
            String rate_for_amount = (String) asd.get("rate_for_amount");
            String rate = (String) asd.get("rate");
            System.out.println(AMOUNT+FROM+"\n"+rate_for_amount+TO+"\nDolar kuru : "+rate);
        }else{
            System.out.println("Bir sorun var!\nSorun kodu : "+durum);
        }

    }
}
