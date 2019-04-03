package com.example.demo.controller;

import com.example.demo.com.example.demo.model.internal.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService prodService;

    @Autowired
    public ProductController(ProductService service){
        this.prodService = service;
    }
    @GetMapping(value="/product", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> product(@RequestParam(required = false, defaultValue = "ShowWasNow") String labelType)
    {
        return ResponseEntity.ok(prodService.reducedPriceProducts(labelType));
    }
}
