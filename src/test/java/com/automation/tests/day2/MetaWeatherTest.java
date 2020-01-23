package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class MetaWeatherTest {

    private String baseURI = "https://www.metaweather.com/api/";

    @Test
    public void test1(){
        Response response = given()
                .baseUri(baseURI+ "location/search/")
                .queryParam("query", "karachi")
                .get();

        assertEquals(200, response.getStatusCode(), response.prettyPrint());
    }

    @Test
    public void test2(){
        Response response =given()
                .pathParam("woeid", "2211096")
                .get(baseURI+ "location/{woeid}");
        System.out.println(response.prettyPrint());
    }


}
