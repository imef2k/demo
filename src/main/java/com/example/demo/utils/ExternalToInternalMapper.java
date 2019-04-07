package com.example.demo.utils;

import com.example.demo.com.example.demo.model.internal.ColorSwatch;
import com.example.demo.com.example.demo.model.internal.Product;
import com.example.demo.model.external.ExternalProduct;
import com.example.demo.model.external.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalToInternalMapper {
    public static List<Product> convert(List<ExternalProduct> externalProduct, String labelType) {
        List<ExternalProduct> externalProd = externalProduct.stream()
                                .filter(p -> priceIsReduced(p))
                                .sorted(ExternalToInternalMapper::comparePrices)
                                .collect(Collectors.toList());


        List<Product> product = externalProd.stream().map(p -> mapper(p, labelType))
                                .collect(Collectors.toList());
        return product;
    }

    private static boolean priceIsReduced(ExternalProduct p) {
        return  !p.getPrice().getNow().equals("") &&
                !p.getPrice().getWas().equals("") &&
                Double.parseDouble(p.getPrice().getNow()) < Double.parseDouble(p.getPrice().getWas());
    }

    private static Product mapper(ExternalProduct externalProduct, String labelType) {
        return  Product.builder()
                          .productId(externalProduct.getProductId())
                          .title(externalProduct.getTitle())
                          .colorSwatches(mapColorSwatches(externalProduct.getColorSwatches()))
                          .nowPrice(priceFormat(externalProduct.getPrice().getNow(), externalProduct.getPrice().getCurrency()))
                          .priceLabel(formatPriceLabel(externalProduct.getPrice(), labelType))
                          .build();
    }

    private static String formatPriceLabel(Price price, String labelType) {
        String now = priceFormat(price.getNow(), price.getCurrency());
        String was = priceFormat(price.getWas(), price.getCurrency());
        String then = price.getThen2().equals("")?price.getThen1():price.getThen2();
        then = priceFormat(then, price.getCurrency());
        String percentDiscount = percentDiscount(price.getWas(), price.getNow());


        if(labelType.equals("ShowWasNow")){
            return String.format("Was %s, now %s", was, now);
        } else if(labelType.equals("ShowWasThenNow")) {
            return String.format("Was %s, then %s, now %s", was, then, now);
        } else if(labelType.equals("ShowPercDscount")) {
            return String.format("%s off - now %s", percentDiscount, now); //x% off - now £y.yy
        } else {
            return String.format("Was %s, now %s", was, now);
        }
    }

    private static String percentDiscount(String was, String now) {
        if(was.equals("")){
            return "";
        } else {
            int  discount = (int) (100 * (Double.parseDouble(was) - Double.parseDouble(now)) / Double.parseDouble(was));
            return String.format("%d%s", discount, "%");
        }
    }

    private static String priceFormat(String price, String currency) {
        if(!price.equals("")){
            if(Double.parseDouble(price) < 10){
               return String.format("%s%s", CurrencyToSymbolConverter.convert(currency), price);
            } else {
                Double priceinDoubleFormat = Double.parseDouble(price);
                return String.format("%s%d", CurrencyToSymbolConverter.convert(currency), priceinDoubleFormat.intValue());
            }
        } else {
            return "";
        }
    }

    private static List<ColorSwatch> mapColorSwatches(List<com.example.demo.model.external.ColorSwatch> colorSwatches) {
        if(colorSwatches == null || colorSwatches.size() == 0) {
            return new ArrayList<ColorSwatch>();
        }
        return colorSwatches.stream().map(c -> colorSwatchesMapper(c)).collect(Collectors.toList());
    }

    private static ColorSwatch colorSwatchesMapper(com.example.demo.model.external.ColorSwatch colorSwatches) {

        return ColorSwatch.builder()
                .color(colorSwatches.getColor())
                .rgbColor(ColorToRGBConverter.convert(colorSwatches.getBasicColor()))
                .skuid(colorSwatches.getSkuId()).build();
    }
    private static int comparePrices(ExternalProduct p1, ExternalProduct p2) {
        int diff1 = (int) (Double.parseDouble(p1.getPrice().getNow()) - Double.parseDouble(p1.getPrice().getWas()));
        int diff2 = (int) (Double.parseDouble(p2.getPrice().getNow()) - Double.parseDouble(p2.getPrice().getWas()));
        return Integer.compare((int)(100 * (Double.parseDouble(p1.getPrice().getNow())/ Double.parseDouble(p1.getPrice().getWas()))),
                               (int)(100 * (Double.parseDouble(p2.getPrice().getNow()) / Double.parseDouble(p2.getPrice().getWas()))));
    }
}
