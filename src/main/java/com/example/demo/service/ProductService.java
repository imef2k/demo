package com.example.demo.service;

import com.example.demo.com.example.demo.model.internal.Product;
import com.example.demo.model.external.ExternalProduct;
import com.example.demo.utils.ExternalToInternalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductService {

    private final ExternalAPI externApi;

    @Autowired
    public ProductService(ExternalAPI externApi){
        this.externApi = externApi;
    }

    public List<Product> reducedPriceProducts(String labelType){

        try {
            List<ExternalProduct> response  = externApi.execute();
            return ExternalToInternalMapper.convert(response, labelType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
