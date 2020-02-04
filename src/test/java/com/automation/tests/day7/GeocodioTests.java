package com.automation.tests.day7;

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

public class GeocodioTests {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("geocodio.uri");
    }

    @Test
    @DisplayName("Test out is giving me city addres")
    public void test(){
        given().
                accept(ContentType.JSON).
                when().
                get("/input").prettyPeek().
                then().assertThat().statusCode(200);
    }



}
