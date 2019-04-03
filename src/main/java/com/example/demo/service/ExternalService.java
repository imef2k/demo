package com.example.demo.service;

import com.example.demo.model.external.WrapperEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  ExternalService {
    @GET("/v1/categories/600001506/products")
    public Call<WrapperEntity> getProducts(@Query("key") String key) ;
}