package com.example.demo;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "integration")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class DemoApplicationTests {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9999);

	@Test
	public void testLabelTypeShowWasNow() throws Exception {
	    setupExternServiceResponse();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/product?labelType=ShowWasNow",String.class);
		String responseJson = response.getBody();
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(responseJson);

		JsonArray products = jsonTree.getAsJsonArray();
		assert(products.size()==2);

		String productId = products.get(0).getAsJsonObject().get("productId").getAsString();
		assert(productId.equals("3418268"));

        String nowPrice = products.get(0).getAsJsonObject().get("nowPrice").getAsString();
        assert(nowPrice.equals("£10"));

        String priceLabel = products.get(0).getAsJsonObject().get("priceLabel").getAsString();
        assert(priceLabel.equals("Was £15, now £10"));

        String color  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString();
        String rgbColor  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("rgbColor").getAsString();
        String skuId  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("skuid").getAsString();

        assert(color.equals("Washed Black"));
        assert(rgbColor.equals("000000"));
        assert(skuId.equals("237345355"));
	}

    @Test
    public void testLabelTypeShowWasThenNow() throws Exception {
        setupExternServiceResponse();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/product?labelType=ShowWasThenNow",String.class);
        String responseJson = response.getBody();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(responseJson);

        JsonArray products = jsonTree.getAsJsonArray();
        assert(products.size()==2);

        String productId = products.get(0).getAsJsonObject().get("productId").getAsString();
        assert(productId.equals("3418268"));

        String nowPrice = products.get(0).getAsJsonObject().get("nowPrice").getAsString();
        assert(nowPrice.equals("£10"));

        String priceLabel = products.get(0).getAsJsonObject().get("priceLabel").getAsString();
        assert(priceLabel.equals("Was £15, then £12, now £10"));

        String color  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString();
        String rgbColor  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("rgbColor").getAsString();
        String skuId  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("skuid").getAsString();

        assert(color.equals("Washed Black"));
        assert(rgbColor.equals("000000"));
        assert(skuId.equals("237345355"));
    }

    @Test
    public void testLabelTypeShowPercDscount() throws Exception {
        setupExternServiceResponse();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/product?labelType=ShowPercDscount",String.class);
        String responseJson = response.getBody();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(responseJson);

        JsonArray products = jsonTree.getAsJsonArray();
        assert(products.size()==2);

        String productId = products.get(0).getAsJsonObject().get("productId").getAsString();
        assert(productId.equals("3418268"));

        String nowPrice = products.get(0).getAsJsonObject().get("nowPrice").getAsString();
        assert(nowPrice.equals("£10"));

        String priceLabel = products.get(0).getAsJsonObject().get("priceLabel").getAsString();
        assert(priceLabel.equals("33% off - now £10"));

        String color  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString();
        String rgbColor  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("rgbColor").getAsString();
        String skuId  = products.get(0).getAsJsonObject().get("colorSwatches").getAsJsonArray().get(0).getAsJsonObject().get("skuid").getAsString();

        assert(color.equals("Washed Black"));
        assert(rgbColor.equals("000000"));
        assert(skuId.equals("237345355"));
    }

    @Test
    public void testProductsOrderedByPriceReduction() throws Exception {
        setupExternServiceResponse();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/product?labelType=ShowPercDscount",String.class);
        String responseJson = response.getBody();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(responseJson);

        JsonArray products = jsonTree.getAsJsonArray();
        assert(products.size()==2);

        String firstProductId = products.get(0).getAsJsonObject().get("productId").getAsString();
        String firstPriceLabel = products.get(0).getAsJsonObject().get("priceLabel").getAsString();

        String secondProductId = products.get(1).getAsJsonObject().get("productId").getAsString();
        String secondPriceLabel = products.get(1).getAsJsonObject().get("priceLabel").getAsString();

        assert(firstProductId.equals("3418268"));
        assert(firstPriceLabel.equals("33% off - now £10"));

        assert(secondProductId.equals("3525085"));
        assert(secondPriceLabel.equals("10% off - now £8.50"));

    }

    @Test
    public void testPriceFormattingCorrectly() throws Exception {
        setupExternServiceResponse();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/product?labelType=ShowPercDscount",String.class);
        String responseJson = response.getBody();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(responseJson);

        JsonArray products = jsonTree.getAsJsonArray();
        assert(products.size()==2);

        String firstProductId = products.get(0).getAsJsonObject().get("productId").getAsString();
        String firstNowPrice = products.get(0).getAsJsonObject().get("nowPrice").getAsString();
        String firstPriceLabel = products.get(0).getAsJsonObject().get("priceLabel").getAsString();

        String secondProductId = products.get(1).getAsJsonObject().get("productId").getAsString();
        String secondNowPrice = products.get(1).getAsJsonObject().get("nowPrice").getAsString();
        String secondPriceLabel = products.get(1).getAsJsonObject().get("priceLabel").getAsString();

        assert(firstProductId.equals("3418268"));
        assert(firstNowPrice.equals("£10"));
        assert(firstPriceLabel.equals("33% off - now £10"));

        assert(secondProductId.equals("3525085"));
        assert(secondNowPrice.equals("£8.50"));
        assert(secondPriceLabel.equals("10% off - now £8.50"));

    }


    private void setupExternServiceResponse() {
        stubFor(get(urlPathEqualTo("/v1/categories/600001506/products"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("externServiceResponse.json")));
    }


}

