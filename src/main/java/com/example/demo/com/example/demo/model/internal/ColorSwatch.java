package com.example.demo.com.example.demo.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ColorSwatch {
    private String color;
    private String rgbColor;
    private String skuid;
}
