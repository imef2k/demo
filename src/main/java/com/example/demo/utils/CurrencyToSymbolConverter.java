package com.example.demo.utils;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CurrencyToSymbolConverter {
    private static Map<String, String> convertionMap;
    static {
        convertionMap = new HashMap<>();
        convertionMap.put("GBP", "£");
        convertionMap.put("USD", "$");
    }
    public static String convert(String currency) {
        String value = convertionMap.get(currency.toUpperCase());
        if(value != null) {
            return value;
        } else {
            return convertionMap.get("GBP");
        }
    }
}
