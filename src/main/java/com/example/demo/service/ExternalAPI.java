package com.example.demo.service;

import com.example.demo.model.external.ExternalProduct;
import com.example.demo.model.external.WrapperEntity;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

public class ExternalAPI {
    @Setter
    private ExternalService externalService;
    public List<ExternalProduct> execute() throws IOException {
        return externalService.getProducts("2ALHCAAs6ikGRBoy6eTHA58RaG097Fma").execute().body().getProducts();
    }
}
