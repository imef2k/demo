package com.example.demo.utils;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorToRGBConverter {
    private static Map<String, String> convertionMap;
    static {
        convertionMap = new HashMap<>();
        convertionMap.put("black", Integer.toHexString(Color.BLACK.getRGB()));
        convertionMap.put("blue", Integer.toHexString(Color.BLUE.getRGB()));
        convertionMap.put("green", Integer.toHexString(Color.GREEN.getRGB()));
        convertionMap.put("grey", Integer.toHexString(Color.GRAY.getRGB()));
        convertionMap.put("orange", Integer.toHexString(Color.ORANGE.getRGB()));
        convertionMap.put("pink", Integer.toHexString(Color.PINK.getRGB()));
        convertionMap.put("purple", Integer.toHexString(Color.MAGENTA.getRGB()));
        convertionMap.put("red", Integer.toHexString(Color.RED.getRGB()));
        convertionMap.put("white", Integer.toHexString(Color.WHITE.getRGB()));
        convertionMap.put("yellow", Integer.toHexString(Color.YELLOW.getRGB()));
    }
    public static String convert(String color) {
        String value = convertionMap.get(color.toLowerCase());
        if(value != null) {
            return value.substring(2, value.length());
        } else {
            value = convertionMap.get("black");
            return value.substring(2, value.length());
        }
    }
}
