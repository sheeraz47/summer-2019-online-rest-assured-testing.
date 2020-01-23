package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeRatesAPITests {

    private String  baseURI = "https://api.exchangeratesapi.io/";

    @Test
    public void test1(){
        Response response = given().get(baseURI + "latest");
//      verify status code
        assertEquals(200, response.getStatusCode(),response.prettyPrint());
//        System.out.println(response.prettyPrint());
    }

    @Test
    public void test2(){
        Response response = given().get(baseURI + "latest");
        assertEquals(200, response.getStatusCode());
//        verify the content type in json both are same
        assertEquals("application/json", response.getHeader("Content-Type"));
//        or like this
        assertEquals("application/json", response.getContentType());

    }

//    GET https://api.exchangeratesapi.io/latest?base=USD HTTP/1.1
//    base it's a query parameter that will ask web service to change currency from eu to something else

    //    #TASK: get currency exchange rate for dollar. By default it's euro.
    @Test
    public void test3(){

        Response response = given().
                queryParam("base","USD").
                get(baseURI+"latest");

        assertEquals(200, response.getStatusCode(), response.prettyPrint());
    }

    //    #TASK: verify that response body, for latest currency rates, contains today's date (2020-01-23 | yyyy-MM-dd)

    @Test
    public void test4(){
        Response response = given().
                queryParam("base", "USD").
                get(baseURI+ "latest");

        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));

        System.out.println("Today's Date: "+todaysDate);
        assertEquals(200, response.getStatusCode(), response.prettyPrint());
        assertTrue(response.getBody().asString().contains("2020-01-23"));

    }

    //let's get currency exchange rate for year 2000
    //GET https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01&base=USD&symbols=ILS,JP

    @Test
    public void test5(){
        Response response = given().
                queryParam("start_at", "2000-01-01").
                queryParam("end_at", "2000-12-31").
                queryParam("base", "USD").
                queryParam("symbols", "EUR,JPY,HKD,NZD" ).
                get(baseURI+ "history");



        assertEquals(200, response.getStatusCode(), response.prettyPrint());

    }

    /*
    Given request parameter "base" is "USD"
     * When user sends request to "api.openrates.io"
     * Then response code should be 200
     * And response body must contain ""base": "USD""
     */

    @Test
    public void test6() {
        Response response = given().
                queryParam("base", "USD").
                get(baseURI+ "latest");

        String body = response.getBody().toString();
        assertEquals(200, response.getStatusCode());

        assertTrue( body.contains("\"base\""));


    }

}
