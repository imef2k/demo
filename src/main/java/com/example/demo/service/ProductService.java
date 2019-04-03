package com.example.demo.service;

import com.example.demo.com.example.demo.model.internal.Product;
import com.example.demo.deserialiser.PriceDeserialiser;
import com.example.demo.model.external.Price;
import com.example.demo.model.external.WrapperEntity;
import com.example.demo.utils.ExternalToInternalMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
public class ProductService {

    public static final String API_BASE_URL = "https://jl-nonprod-syst.apigee.net/";

    public List<Product> reducedPriceProducts(String labelType){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Price.class, new PriceDeserialiser())
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        ExternalService service = retrofit.create(ExternalService.class);
        Call<WrapperEntity>  productCall  = service.getProducts("2ALHCAAs6ikGRBoy6eTHA58RaG097Fma");
        try {
           Response<WrapperEntity> response  = productCall.execute();
           return ExternalToInternalMapper.convert(response.body().getProducts(), labelType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
