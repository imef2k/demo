package com.example.demo.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorToRGBConverterTest {
    @Test
    public void testColorToRGBToHexConversion(){
        assertEquals("000000", ColorToRGBConverter.convert("black"));
        assertEquals("0000ff", ColorToRGBConverter.convert("blue"));
        assertEquals("00ff00", ColorToRGBConverter.convert("green"));
        assertEquals("808080", ColorToRGBConverter.convert("grey"));
        assertEquals("ffc800", ColorToRGBConverter.convert("orange"));
        assertEquals("ffafaf", ColorToRGBConverter.convert("pink"));
        assertEquals("ff00ff", ColorToRGBConverter.convert("purple"));
        assertEquals("ff0000", ColorToRGBConverter.convert("red"));
        assertEquals("ffffff", ColorToRGBConverter.convert("white"));
        assertEquals("ffff00", ColorToRGBConverter.convert("yellow"));
    }

    @Test
    public void testUnmappedColorDefaultstoBlack() {
        assertEquals("000000", ColorToRGBConverter.convert("unknownColor"));
    }
}