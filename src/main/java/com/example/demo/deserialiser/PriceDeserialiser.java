package com.example.demo.deserialiser;

import com.example.demo.model.external.Price;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PriceDeserialiser implements JsonDeserializer<Price> {
    @Override
    public Price deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Price price = new Price();

        JsonObject jobject = jsonElement.getAsJsonObject();

        price.setWas(jobject.get("was").getAsString());
        price.setCurrency(jobject.get("currency").getAsString());
        price.setThen1(jobject.get("then1").getAsString());
        price.setThen2(jobject.get("then2").getAsString());

        if(jobject.get("now").isJsonObject()) {
            price.setNow(jobject.get("now").getAsJsonObject().get("to").getAsString());
        } else {
            price.setNow(jobject.get("now").getAsString());
        }
        return price;
    }
}
