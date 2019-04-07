package com.example.demo.config;

import com.example.demo.deserialiser.PriceDeserialiser;
import com.example.demo.model.external.Price;
import com.example.demo.model.external.WrapperEntity;
import com.example.demo.service.ExternalAPI;
import com.example.demo.service.ExternalService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class DemoConfig {

    @Bean
    public ExternalAPI externalClient(@Value("${external_api.baseUrl}") String extApiBaseUrl) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Price.class, new PriceDeserialiser())
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(extApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        ExternalService service = retrofit.create(ExternalService.class);
        ExternalAPI externApi = new ExternalAPI();
        externApi.setExternalService(service);
        return externApi;
    }
}
