package com.example.demo.model.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExternalProduct {
    private String productId;
    private String title;
    private List<ColorSwatch> colorSwatches;
    private Price price;
}
