package com.automation.tests.day8;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class OMDBTestsAPIKey {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("omdb.uri");
    }

    @Test
    @DisplayName("Verify that unauthorized user cannot get info about movies from OMDB api")
    public void test1(){
        given().
                contentType(ContentType.JSON).
                queryParam("t", "Home Alone").
                when().
                get().prettyPeek().
                then().assertThat().statusCode(401).body("Error", is("No API key provided."));
//                401 Unauthorized = you are not allowed to access this web service
    }

    @Test
    @DisplayName("Verify that Macaulay Culkin appears in actors list from Home ALone")
    public void test2(){
        Response response = given().
                                    accept(ContentType.JSON).
                                    queryParam("apikey", "9f94d4d0").
                                    queryParam("t", "Home Alone").
                            when().
                                    get().prettyPeek();
        response.then().assertThat().statusCode(200).body("Actors", containsString("Macaulay Culkin" ));

//        if i want to take all the keys and values we use map
        Map<String, ?> payload = response.getBody().as(Map.class);
        System.out.println(payload);// print all the key value
//        iterat the Map by using Map.Entry and entrySet
        for(Map.Entry<String, ?> entry : payload.entrySet()){
            System.out.println("Key: "+entry.getKey()+", Value: "+entry.getValue());
        }




    }
}
