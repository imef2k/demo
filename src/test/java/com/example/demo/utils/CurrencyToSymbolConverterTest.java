package com.example.demo.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyToSymbolConverterTest {
    @Test
    public void testCurrencyConvertsToSymbol() {
        assertEquals("£", CurrencyToSymbolConverter.convert("GBP"));
        assertEquals("$", CurrencyToSymbolConverter.convert("USD"));
    }
    @Test
    public void testUnknownCurrencyDefaultsToGBPounds() {
        assertEquals("£", CurrencyToSymbolConverter.convert("UNKNOWN"));
    }
}